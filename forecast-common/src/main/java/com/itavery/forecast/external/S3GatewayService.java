package com.itavery.forecast.external;

import javax.ws.rs.core.Response;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-17
 * https://github.com/helloavery
 */

public interface S3GatewayService {

    String retrieveSecrets(String bucket, String bucketObject);

    Response sendSecrets(String bucket, String bucketObject, String data);
}
