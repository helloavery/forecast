package com.itavery.forecast.external;

import com.google.common.base.Stopwatch;
import com.itavery.forecast.S3GatewayDTO;
import com.itavery.forecast.bootconfig.ProgramArguments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.inject.Inject;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-17
 * https://github.com/helloavery
 */

@Service
public class S3GatewayServiceImpl implements S3GatewayService{

    private static final Logger LOGGER = LogManager.getLogger(S3GatewayServiceImpl.class);

    private ExecutorService executor = Executors.newFixedThreadPool(4);

    private ProgramArguments programArguments;
    private final RestTemplate restTemplate;
    private Cipher cipher;
    private KeyGenerator keyGenerator;
    private Signature signature;

    @Inject
    public S3GatewayServiceImpl(ProgramArguments programArguments, final RestTemplate restTemplate){
        this.programArguments = programArguments;
        this.restTemplate = restTemplate;
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
    public Future<String> retrieveSecrets(String bucket, String bucketObject){
        try{
            return executor.submit(() -> {
                LOGGER.info("Retrieving secrets for bucket {} and object {}", bucket,bucketObject);
                KeyPair keyPair = generateKeyPair(RSA, 2048);
                S3GatewayDTO symmetricKeyAndUUID = retrieveS3GatewaySymmetricKey(keyPair.getPublic().getEncoded());
                SecretKey secretKey = decryptSymmetricKey(symmetricKeyAndUUID.getSymmetricKey(), keyPair.getPrivate());
                S3GatewayDTO s3GatewayRequest = new S3GatewayDTO(bucket, bucketObject, symmetricKeyAndUUID.getSymmetricKeyUUID());
                S3GatewayDTO s3GatewayResponse = restTemplate.postForObject(programArguments.getS3gatewayendpoint() + RETRIEVE_BUCKET_OBJECT, s3GatewayRequest, S3GatewayDTO.class);
                if(s3GatewayResponse == null){
                    LOGGER.error("Error making call to S3 Gateway Service to retrieve secrets for bucket {} and object {}", bucket,bucketObject);
                    throw new RuntimeException("Error making call to S3 Gateway Service to retrieve secrets");
                }
                PublicKey decodedPublicKey = KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(s3GatewayResponse.getPublicKey()));
                byte[] decryptedData = decryptData(s3GatewayResponse.getCipherText(),secretKey);
                if(!verifySignature(decodedPublicKey,decryptedData,s3GatewayResponse.getSignature())){
                    LOGGER.error("Error retrieving bucket object {}, signature verification came back as false", bucketObject);
                    throw new RuntimeException("Error retrieving bucket object, signature verification came back as false");
                }
                return new String(decryptedData,StandardCharsets.UTF_8);
            });
        }
        catch(Exception e){
            LOGGER.error("Error retrieving bucket object {}", bucketObject);
            throw new RuntimeException("Error retrieving bucket object", e);
        }
    }

    @Override
    public boolean sendSecrets(String bucket, String bucketObject, String data) {
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
                throw new RuntimeException("Error making call to S3 Gateway Service to upload secrets");
            }
            stopwatch.stop();
            LOGGER.info("Upload to S3 bucket complete, time took is {}", stopwatch.toString());
            return successfulUpload;
        } catch (Exception e) {
            LOGGER.error("Error sending bucket object {}", bucketObject);
            throw new RuntimeException("Error sending bucket object", e);
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
