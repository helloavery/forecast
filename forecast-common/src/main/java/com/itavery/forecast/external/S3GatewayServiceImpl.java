package com.itavery.forecast.external;

import com.google.common.base.Stopwatch;
import com.itavery.forecast.ResponseBuilder;
import com.itavery.forecast.S3GatewayDTO;
import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-17
 * https://github.com/helloavery
 */

@Service
public class S3GatewayServiceImpl<T> implements S3GatewayService{

    private static final Logger LOGGER = LogManager.getLogger(S3GatewayServiceImpl.class);
    @Inject
    private ProgramArguments programArguments;
    @Inject
    private RestTemplate restTemplate;
    @Inject
    ResponseBuilder responseBuilder;
    private Cipher cipher;
    private KeyGenerator keyGenerator;
    private Signature signature;

    public S3GatewayServiceImpl(){
        setup();
    }

    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    private static final String SHA256 = "SHA256WithRSA";
    private static final String AES = "AES";
    private static final String RSA = "RSA";
    private static final String SEND_SYMMETRIC_KEY_REQUEST = "/rest/v1/crypto/generateSymmetricKey";
    private static final String ADD_BUCKET_OBJECT = "/rest/v1/s3bucketOperations/uploadAsset";
    private static final String RETRIEVE_BUCKET_OBJECT = "/rest/v1/s3bucketOperations/getItemRequest";

    private void setup(){
        try{
            Security.addProvider(new BouncyCastleProvider());
            this.keyGenerator = KeyGenerator.getInstance(AES);
            this.signature = Signature.getInstance(SHA256);
        }
        catch(Exception e){
            LOGGER.error("Error initiating S3 Service");
            throw new RuntimeException("Error initiating S3 Service", e);
        }
    }

    @Override
    public String retrieveSecrets(String bucket, String bucketObject){
        try{
            LOGGER.info("Retrieving secrets for bucket {} and object {}", bucket,bucketObject);
            KeyPair keyPair = generateKeyPair(RSA, 2048);
            S3GatewayDTO symmetricKeyAndUUID = retrieveS3GatewaySymmetricKey(keyPair.getPublic().getEncoded());
            SecretKey secretKey = decryptSymmetricKey(symmetricKeyAndUUID.getSymmetricKey(), keyPair.getPrivate());
            S3GatewayDTO s3GatewayRequest = new S3GatewayDTO(bucket, bucketObject, symmetricKeyAndUUID.getSymmetricKeyUUID());
            HttpEntity<S3GatewayDTO> request = new HttpEntity<>(s3GatewayRequest);
            ResponseEntity<S3GatewayDTO> s3GatewayResponse = restTemplate.exchange(programArguments.getS3gatewayendpoint() + RETRIEVE_BUCKET_OBJECT, HttpMethod.POST, request, S3GatewayDTO.class);
            if(s3GatewayResponse.getStatusCodeValue() != 200){
                LOGGER.error("Error making call to S3 Gateway Service to retrieve secrets for bucket {} and object {}, error {}", bucket,bucketObject, s3GatewayResponse.getStatusCodeValue());
                throw new RuntimeException("Error with communicating with S3 Gateway");
            }
            S3GatewayDTO s3GatewayDTOBody = s3GatewayResponse.getBody();
            PublicKey decodedPublicKey = KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(s3GatewayDTOBody.getPublicKey()));
            byte[] decryptedData = decryptData(s3GatewayDTOBody.getCipherText(),secretKey);
            if(!verifySignature(decodedPublicKey,decryptedData,s3GatewayDTOBody.getSignature())){
                LOGGER.error("Error retrieving bucket object {}, signature verification came back as false", bucketObject);
                throw new RuntimeException("Error fetching bucket, signature verification came back as false");
            }
            return new String(decryptedData,StandardCharsets.UTF_8);
        }
        catch(Exception e){
            LOGGER.error("Error retrieving bucket object {}", bucketObject);
            throw new RuntimeException("Error fetching bucket");
        }
    }

    @Override
    public Response sendSecrets(String bucket, String bucketObject, String data) {
        try {
            LOGGER.info("Starting Uploading Secrets Job");
            Stopwatch stopwatch = Stopwatch.createStarted();
            KeyPair keyPair = generateKeyPair(RSA, 2048);
            S3GatewayDTO symmetricKeyAndUUID = retrieveS3GatewaySymmetricKey(keyPair.getPublic().getEncoded());
            SecretKey secretKey = decryptSymmetricKey(symmetricKeyAndUUID.getSymmetricKey(), keyPair.getPrivate());
            byte[] encryptedData = encryptData(data.getBytes(StandardCharsets.UTF_8), secretKey);
            byte[] digitalSignature = generateSignature(keyPair, data.getBytes(StandardCharsets.UTF_8));
            S3GatewayDTO s3GatewayRequest = new S3GatewayDTO(bucket, bucketObject, encryptedData, symmetricKeyAndUUID.getSymmetricKeyUUID(), keyPair.getPublic().getEncoded(), digitalSignature);
            Boolean successfulUpload = restTemplate.postForObject(programArguments.getS3gatewayendpoint() + ADD_BUCKET_OBJECT, s3GatewayRequest, boolean.class);
            if (successfulUpload == null) {
                LOGGER.error("Error making call to S3 Gateway Service to upload secrets for bucket {} and object {}", bucket, bucketObject);
                return responseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_S3_GATEWAY_ERROR);
            }
            stopwatch.stop();
            LOGGER.info("Upload to S3 bucket complete, time took is {}", stopwatch.toString());
            return responseBuilder.createSuccessResponse(successfulUpload);
        } catch (Exception e) {
            LOGGER.error("Error sending bucket object {}", bucketObject);
            return responseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_S3_GATEWAY_BUCKET_SEND_ERROR);
        }
    }

    private S3GatewayDTO retrieveS3GatewaySymmetricKey(byte[] publicKey){
        try{
            S3GatewayDTO requestedEncodedPublicKey = restTemplate.postForObject(programArguments.getS3gatewayendpoint() + SEND_SYMMETRIC_KEY_REQUEST, publicKey, S3GatewayDTO.class);
            if(requestedEncodedPublicKey == null){
                LOGGER.error("Error making call to S3 Gateway Service to generated and receive pub key");
                throw new RuntimeException("Error making call to S3 Gateway Service to generated and receive pub key");
            }
            return requestedEncodedPublicKey;
        }
        catch(Exception e){
            LOGGER.error("Error retrieving encrypted encoded public key");
            throw new RuntimeException("Error retrieving encrypted encoded public key",e);
        }
    }


    private KeyPair generateKeyPair(String algorithm, Integer keySize){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            if(keySize != null){
                keyPairGenerator.initialize(keySize);
            }
            return keyPairGenerator.generateKeyPair();
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }

    private byte[] encryptData(byte[] data, SecretKey secretKey) throws Exception{
        try{
            cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);;
            return cipher.doFinal(data);
        }
        catch(Exception e){
            LOGGER.error("Error encrypting data");
            throw e;
        }
    }

    private SecretKey decryptSymmetricKey(byte[] symmetricKey, PrivateKey privateKey) throws Exception{
        try{
            cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.UNWRAP_MODE, privateKey);
            return (SecretKey) cipher.unwrap(symmetricKey, AES, Cipher.SECRET_KEY);
        }
        catch(Exception e){
            LOGGER.error("Error decrypting data");
            throw e;
        }
    }

    private byte[] decryptData(byte[] cipherText, SecretKey secretKey) throws Exception{
        try{
            cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(cipherText);
        }
        catch(Exception e){
            LOGGER.error("Error decrypting data");
            throw e;
        }
    }

    private byte[] generateSignature(KeyPair keyPair, byte[] data) throws Exception{
        try{
            SecureRandom secureRandom = new SecureRandom();
            signature.initSign(keyPair.getPrivate(),secureRandom);
            signature.update(data);
            return signature.sign();
        }
        catch(Exception e){
            LOGGER.error("Error generating signature for data");
            throw e;
        }
    }

    private boolean verifySignature(PublicKey publicKey, byte[] data, byte[] digitalSignature) throws Exception{
        try{
            LOGGER.info("Verifying signature retrieved from S3 Gateway Service");
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(digitalSignature);
        }
        catch(Exception e){
            LOGGER.error("Error verifying signature");
            throw e;
        }
    }
}
