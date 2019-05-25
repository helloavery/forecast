package com.itavery.forecast.concurrent;

import com.itavery.forecast.external.S3GatewayService;

import javax.ws.rs.core.Response;
import java.util.concurrent.Callable;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-18
 * https://github.com/helloavery
 */

public class SecretRetrievalTask implements Callable<Response>{

    private S3GatewayService s3GatewayService;
    private String bucket;
    private String bucketObject;

    public SecretRetrievalTask(S3GatewayService s3GatewayService, String bucket, String bucketObject){
        this.s3GatewayService = s3GatewayService;
        this.bucket = bucket;
        this.bucketObject = bucketObject;
    }

    @Override
    public Response call() throws Exception {
        return s3GatewayService.retrieveSecrets(bucket, bucketObject);
    }
}
