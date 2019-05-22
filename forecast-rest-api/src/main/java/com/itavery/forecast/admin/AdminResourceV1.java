package com.itavery.forecast.admin;

import com.itavery.forecast.FileReader;
import com.itavery.forecast.external.S3GatewayService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-01-10
 * https://github.com/helloavery
 */

@RestController
@RequestMapping("v1/admin")
public class AdminResourceV1 {

    @Inject
    private S3GatewayService s3GatewayService;

    @RequestMapping(value = "/uploadSecrets/{bucket}/{bucketObject}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response uploadAppSecrets(@PathVariable("bucket") String bucket, @PathVariable("bucketObject") String bucketObject, @RequestBody String data){
        s3GatewayService.sendSecrets(bucket, bucketObject, data);
        return Response.ok().build();
    }

    @RequestMapping(value = "/uploadSecretsFromFile/{bucket}/{bucketObject}", method = RequestMethod.POST, consumes = {"application/json", "text/plain"}, produces = "application/json")
    public boolean uploadAppSecretsFromFile(@PathVariable("bucket") String bucket, @PathVariable("bucketObject") String bucketObject, @RequestBody String file){
        String data = FileReader.readDataFromFile(file);
        return s3GatewayService.sendSecrets(bucket, bucketObject, data);
    }
}
