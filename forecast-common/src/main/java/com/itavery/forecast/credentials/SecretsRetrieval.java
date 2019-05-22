package com.itavery.forecast.credentials;

import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.concurrent.ExecutorServiceBase;
import com.itavery.forecast.concurrent.SecretRetrievalTask;
import com.itavery.forecast.external.S3GatewayService;
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
    @Inject
    private ProgramArguments programArguments;
    @Inject
    private S3GatewayService s3GatewayService;
    @Inject
    private ExecutorServiceBase executorService;

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
            twilioSid = twilioSecretsContainer.getkey();
            twilioToken = twilioSecretsContainer.getValue();
            keyringKey = keyringSecretsContainer.getkey();
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
