package com.itavery.forecast.interaction.client;


import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.external.AuthyResponse;
import com.itavery.forecast.external.S3GatewayDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-07-17
 * https://github.com/helloavery
 */

@Named
public class ClientRestManager {

    private ProgramArguments programArguments;
    private ResteasyClient client;
    private ResteasyWebTarget target;

    public ClientRestManager(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        cm.setMaxTotal(200); // Increase max total connection to 200
        cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
        ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine(httpClient);
        client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
    }

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    /**** AuthyClient Calls *****/
    public AuthyResponse sendSMSOTP(String apiKey, int auhtyId){
        setResteasyWebTarget(programArguments.getAuthyClientBasePath());
        AuthyClient simpleClient = getSimpleClient(AuthyClient.class);
        return simpleClient.sendSMSOTP(apiKey, auhtyId);
    }

    public AuthyResponse sendVoiceOTP(String apiKey, int auhtyId){
        setResteasyWebTarget(programArguments.getAuthyClientBasePath());
        AuthyClient simpleClient = getSimpleClient(AuthyClient.class);
        return simpleClient.sendVoiceOTP(apiKey, auhtyId);
    }

    public AuthyResponse verifyOTP(String apiKey, String token, int auhtyId){
        setResteasyWebTarget(programArguments.getAuthyClientBasePath());
        AuthyClient simpleClient = getSimpleClient(AuthyClient.class);
        return simpleClient.verifyOTP(apiKey, token, auhtyId);
    }

    /*** MailgunClient calls ***/
    public HttpResponse<JsonNode> verifyEmailAddress(String basicAuth, String address){
        setResteasyWebTarget(programArguments.getMailgunClientBasePath());
        MailgunClient simpleClient = getSimpleClient(MailgunClient.class);
        return simpleClient.verifyEmailAddress(basicAuth, address);
    }

    /*** S3GatewayClient calls ***/
    public S3GatewayDTO retrieveSymmetricKey(byte[] publicKey){
        setResteasyWebTarget(programArguments.getS3GatewayBasePath());
        S3GatewayClient simpleClient = getSimpleClient(S3GatewayClient.class);
        return simpleClient.retrieveSymmetricKey(publicKey);
    }

    public boolean uploadBucketObject(S3GatewayDTO s3GatewayRequest){
        setResteasyWebTarget(programArguments.getS3GatewayBasePath());
        S3GatewayClient simpleClient = getSimpleClient(S3GatewayClient.class);
        return simpleClient.uploadBucketObject(s3GatewayRequest);
    }

    public S3GatewayDTO retrieveBucketObject(S3GatewayDTO s3GatewayRequest){
        setResteasyWebTarget(programArguments.getS3GatewayBasePath());
        S3GatewayClient simpleClient = getSimpleClient(S3GatewayClient.class);
        return simpleClient.retrieveBucketObject(s3GatewayRequest);
    }

    private void setResteasyWebTarget(String basePath){
        target = client.target(UriBuilder.fromPath(basePath));
    }

    private <T> T getSimpleClient(Class<T> clazzOfT){
        return target.proxy(clazzOfT);
    }

}
