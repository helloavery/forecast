package com.itavery.forecast.external;

import com.itavery.forecast.S3GatewayDTO;
import com.itavery.forecast.bootconfig.ProgramArguments;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-17
 * https://github.com/helloavery
 */

@Service
public class S3GatewayServiceImpl implements S3GatewayService{

    private static final Logger LOGGER = LogManager.getLogger(S3GatewayServiceImpl.class);

    private ProgramArguments programArguments;
    private S3GatewayOperation s3GatewayOperation;
    private final RestTemplate restTemplate;
    private Cipher cipher;
    private Signature signature;

    @Inject
    public S3GatewayServiceImpl(ProgramArguments programArguments, final RestTemplate restTemplate, S3GatewayOperation s3GatewayOperation){
        this.programArguments = programArguments;
        this.s3GatewayOperation = s3GatewayOperation;
        this.restTemplate = restTemplate;
        init();
    }

    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    private static final String SHA256 = "SHA256WithRSA";
    private static final String RSA = "RSA";
    private static final String DSA = "DSA";
    private static final String REQUEST_PUB_KEY = "/rest/v1/crypto/requestPubKey";
    private static final String ADD_BUCKET_OBJECT = "/rest/v1/s3bucketOperations/uploadAsset";
    private static final String RETRIEVE_BUCKET_OBJECT = "/rest/v1/s3bucketOperations/getItemRequest";

    private void init(){
        try{
            s3GatewayOperation.setup();
            this.cipher = s3GatewayOperation.cipherGetInstance(RSA);
            this.signature = s3GatewayOperation.initSignature(SHA256);
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
            KeyPair keyPair = s3GatewayOperation.generateKeyPair(RSA, 2048);
            S3GatewayDTO s3GatewayRequest = new S3GatewayDTO(bucket, bucketObject, keyPair.getPublic().getEncoded());
            S3GatewayDTO s3GatewayResponse = restTemplate.postForObject(programArguments.getS3gatewayendpoint() + RETRIEVE_BUCKET_OBJECT, s3GatewayRequest, S3GatewayDTO.class);
            if(s3GatewayResponse == null){
                LOGGER.error("Error making call to S3 Gateway Service to retrieve secrets for bucket {} and object {}", bucket,bucketObject);
                throw new RuntimeException("Error making call to S3 Gateway Service to retrieve secrets");
            }
            PublicKey decodedPublicKey = KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(s3GatewayResponse.getPublicKey()));
            byte[] decryptedData = decryptSecrets(keyPair.getPrivate(), s3GatewayResponse.getCipherText());
            if(!verifySignature(decodedPublicKey,decryptedData,s3GatewayResponse.getSignature())){
                LOGGER.error("Error retrieving bucket object {}", bucketObject);
                throw new RuntimeException("Error retrieving bucket object");
            }
            return new String(decryptedData,StandardCharsets.UTF_8);
        }
        catch(Exception e){
            LOGGER.error("Error retrieving bucket object {}", bucketObject);
            throw new RuntimeException("Error retrieving bucket object", e);
        }
    }

    @Override
    public void sendSecrets(String bucket, String bucketObject, String data){
        try{
            long ehcacheVariable = new SecureRandom().nextLong();
            byte[] requestedEncodedPublicKey = restTemplate.postForObject(programArguments.getS3gatewayendpoint() + REQUEST_PUB_KEY, ehcacheVariable, byte[].class);
            if(requestedEncodedPublicKey == null){
                LOGGER.error("Error making call to S3 Gateway Service to generated and receive pub key");
                throw new RuntimeException("Error making call to S3 Gateway Service to generated and receive pub key");
            }
            KeyPair keyPair = s3GatewayOperation.generateKeyPair(SHA256, 2048);
            PublicKey decodedPublicKey = KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(requestedEncodedPublicKey));
            byte[] encryptedData = encryptSecrets(data, decodedPublicKey);
            byte[] digitalSignature = generateSignature(keyPair, encryptedData);
            S3GatewayDTO s3GatewayRequest = new S3GatewayDTO(bucket, bucketObject, encryptedData, ehcacheVariable, keyPair.getPublic().getEncoded(), digitalSignature);
            Boolean successfulUpload = restTemplate.postForObject(programArguments.getS3gatewayendpoint() + ADD_BUCKET_OBJECT, s3GatewayRequest, Boolean.class);
            if(successfulUpload == null){
                LOGGER.error("Error making call to S3 Gateway Service to upload secrets for bucket {} and object {}", bucket,bucketObject);
                throw new RuntimeException("Error making call to S3 Gateway Service to upload secrets");
            }
            if(!successfulUpload){
                LOGGER.error("Error occurred in S3 Gateway Service while trying to upload secrets for bucket {} and object {}", bucket,bucketObject);
                throw new RuntimeException("Error occurred in S3 Gateway Service while trying to upload secrets");
            }
        }
        catch(Exception e){
            LOGGER.error("Error sending bucket object {}", bucketObject);
            throw new RuntimeException("Error sending bucket object", e);
        }
    }

    private byte[] encryptSecrets(String data, PublicKey publicKey) throws Exception{
        try{
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] dataInBytes = data.getBytes(StandardCharsets.UTF_8);
            return cipher.doFinal(dataInBytes);
        }
        catch(Exception e){
            LOGGER.error("Error encrypting data");
            throw e;
        }
    }

    private byte[] decryptSecrets(PrivateKey privateKey, byte[] cipherText) throws Exception{
        try{
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
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
