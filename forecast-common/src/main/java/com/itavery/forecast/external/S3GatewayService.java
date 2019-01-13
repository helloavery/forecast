package com.itavery.forecast.external;

import java.util.concurrent.Future;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-17
 * https://github.com/helloavery
 */

public interface S3GatewayService {

    Future<String> retrieveSecrets(String bucket, String bucketObject);

    boolean sendSecrets(String bucket, String bucketObject, String data);
}
