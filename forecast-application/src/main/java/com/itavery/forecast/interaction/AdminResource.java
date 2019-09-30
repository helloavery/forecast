package com.itavery.forecast.interaction;

import com.averygrimes.core.pojo.RoleValues;
import com.averygrimes.core.service.S3GatewayService;
import com.averygrimes.core.util.annotation.EntitlementPolicy;
import com.itavery.forecast.utils.FileReader;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-01-10
 * https://github.com/helloavery
 */

@Component
@Path("/admin")
public class AdminResource {

    private S3GatewayService s3GatewayService;
    @Context
    HttpServletRequest httpServletRequest;

    @Inject
    public void setS3GatewayService(S3GatewayService s3GatewayService) {
        this.s3GatewayService = s3GatewayService;
    }

    @POST
    @Path("/uploadSecrets/{bucket}/{bucketObject}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @EntitlementPolicy(role = RoleValues.ADMIN)
    public Response uploadAppSecrets(@PathParam("bucket") String bucket, @PathParam("bucketObject") String bucketObject, String data){
        return s3GatewayService.sendSecrets(bucket, bucketObject, data);
    }

    @POST
    @Path("/uploadSecretsFromFile/{bucket}/{bucketObject}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @EntitlementPolicy(role = RoleValues.ADMIN)
    public Response uploadAppSecretsFromFile(@PathParam("bucket") String bucket, @PathParam("bucketObject") String bucketObject, String file){
        String data = FileReader.readDataFromFile(file);
        return s3GatewayService.sendSecrets(bucket, bucketObject, data);
    }
}
