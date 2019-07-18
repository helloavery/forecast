package com.itavery.forecast.external;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-17
 * https://github.com/helloavery
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class S3GatewayDTO {

    private String bucket;
    private String bucketObject;
    private byte[] cipherText;
    private byte[] publicKey;
    private String symmetricKeyUUID;
    private byte[] symmetricKey;
    private byte[] signature;

    public S3GatewayDTO(){
        super();
    }

    public S3GatewayDTO(String bucket, String bucketObject, String symmetricKeyUUID){
        this.bucket = bucket;
        this.bucketObject = bucketObject;
        this.symmetricKeyUUID = symmetricKeyUUID;
    }

    public S3GatewayDTO(String bucket, String bucketObject, byte[] cipherText, String symmetricKeyUUID, byte[] publicKey, byte[] signature){
        this.bucket = bucket;
        this.bucketObject = bucketObject;
        this.cipherText = cipherText;
        this.symmetricKeyUUID = symmetricKeyUUID;
        this.publicKey = publicKey;
        this.signature = signature;
    }

    public String getBucket() {
        return bucket;
    }

    public String getBucketObject() {
        return bucketObject;
    }

    public byte[] getCipherText() {
        return cipherText;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public String getSymmetricKeyUUID() {
        return symmetricKeyUUID;
    }

    public byte[] getSymmetricKey() {
        return symmetricKey;
    }

    public byte[] getSignature() {
        return signature;
    }
}
