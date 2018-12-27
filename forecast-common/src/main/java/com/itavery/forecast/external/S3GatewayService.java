package com.itavery.forecast.external;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-17
 * https://github.com/helloavery
 */

public interface S3GatewayService {

    String retrieveSecrets(String bucket, String bucketObject);

    void sendSecrets(String bucket, String bucketObject, String data);
}
