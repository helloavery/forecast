package com.itavery.forecast.interaction.client;

import com.itavery.forecast.external.S3GatewayDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-07-17
 * https://github.com/helloavery
 */

public interface S3GatewayClient {

    @POST
    @Path("/crypto/generateSymmetricKey")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    S3GatewayDTO retrieveSymmetricKey(byte[] publicKey);

    @POST
    @Path("/s3bucketOperations/uploadAsset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean uploadBucketObject(S3GatewayDTO s3GatewayRequest);

    @POST
    @Path("/s3bucketOperations/getItemRequest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    S3GatewayDTO retrieveBucketObject(S3GatewayDTO s3GatewayRequest);

}
