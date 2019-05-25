package com.itavery.forecast.admin;

import com.itavery.forecast.FileReader;
import com.itavery.forecast.annotation.EntitlementPolicy;
import com.itavery.forecast.enums.RoleValues;
import com.itavery.forecast.external.S3GatewayService;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@Path("v1/admin")
public class AdminResourceV1 {

    @Inject
    private S3GatewayService s3GatewayService;
    @Context
    HttpServletRequest httpServletRequest;

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
