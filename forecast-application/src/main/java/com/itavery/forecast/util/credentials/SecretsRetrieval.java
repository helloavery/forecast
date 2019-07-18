package com.itavery.forecast.util.credentials;

import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.external.S3GatewayService;
import com.itavery.forecast.util.concurrent.ExecutorServiceBase;
import com.itavery.forecast.util.concurrent.SecretRetrievalTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-19
 * https://github.com/helloavery
 */

@Component
public class SecretsRetrieval implements InitializingBean{

    private static final Logger LOGGER = LogManager.getLogger(SecretsRetrieval.class);
    private ProgramArguments programArguments;
    private S3GatewayService s3GatewayService;
    private ExecutorServiceBase executorService;

    @Inject
    public SecretsRetrieval(ProgramArguments programArguments, S3GatewayService s3GatewayService, ExecutorServiceBase executorService){
        this.programArguments = programArguments;
        this.s3GatewayService = s3GatewayService;
        this.executorService = executorService;
    }

    private static String mailgunApiKey;
    private static String authyApiKey;
    private static String twilioSid;
    private static String twilioToken;
    private static String keyringKey;
    private static String keyringValue;

    @Override
    public void afterPropertiesSet(){
        Future<String> mailgunFuture = executorService.submit(new SecretRetrievalTask(s3GatewayService,programArguments.getS3bucket(),programArguments.getS3bucketObjectMailgun()));
        Future<String> authyFuture = executorService.submit(new SecretRetrievalTask(s3GatewayService,programArguments.getS3bucket(),programArguments.getS3bucketObjectAuthy()));
        Future<String> twilioFuture = executorService.submit(new SecretRetrievalTask(s3GatewayService,programArguments.getS3bucket(),programArguments.getS3bucketObjectTwilio()));
        Future<String> keyringFuture = executorService.submit(new SecretRetrievalTask(s3GatewayService,programArguments.getS3bucket(),programArguments.getS3bucketObjectKeyring()));
        try{
            String mailgunSecrets = mailgunFuture.get(5000, TimeUnit.MILLISECONDS);
            String authySecrets = authyFuture.get(5000, TimeUnit.MILLISECONDS);
            String twilioSecrets = twilioFuture.get(5000, TimeUnit.MILLISECONDS);
            String keyringSecrets = keyringFuture.get(5000, TimeUnit.MILLISECONDS);
            ISecretsContainer mailgunSecretsContainer = new SecretsContainer(mailgunSecrets,false);
            ISecretsContainer authySecretsContainer = new SecretsContainer(authySecrets,false);
            ISecretsContainer twilioSecretsContainer = new SecretsContainer(twilioSecrets,true);
            ISecretsContainer keyringSecretsContainer = new SecretsContainer(keyringSecrets,true);
            mailgunApiKey = mailgunSecretsContainer.getApiKey();
            authyApiKey = authySecretsContainer.getApiKey();
            twilioSid = twilioSecretsContainer.getKey();
            twilioToken = twilioSecretsContainer.getValue();
            keyringKey = keyringSecretsContainer.getKey();
            keyringValue = keyringSecretsContainer.getValue();
        }
        catch(Exception e){
            LOGGER.error("Error initializing secrets");
            throw new RuntimeException("Error initializing secrets", e);
        }
    }

    public String getMailgunApiKey() {
        return mailgunApiKey;
    }

    public String getAuthyApiKey() {
        return authyApiKey;
    }

    public String getTwilioSid(){
        return twilioSid;
    }

    public String getTwilioToken() {
        return twilioToken;
    }

    public String getKeyringKey() {
        return keyringKey;
    }

    public String getKeyringValue() {
        return keyringValue;
    }
}
