package com.itavery.forecast.credentials;

import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.external.S3GatewayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-19
 * https://github.com/helloavery
 */

@Component
public class SecretsRetrieval implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(SecretsRetrieval.class);

    private final ProgramArguments programArguments;
    private final S3GatewayService s3GatewayService;

    public SecretsRetrieval(final ProgramArguments programArguments, final S3GatewayService s3GatewayService){
        this.programArguments = programArguments;
        this.s3GatewayService = s3GatewayService;
    }

    private static String mailgunApiKey;
    private static String authyApiKey;
    private static String twilioSid;
    private static String twilioToken;
    private static String keyringKey;
    private static String keyringValue;

    @Override
    public void afterPropertiesSet(){
        try{
            ISecretsContainer mailgunSecretsContainer = new SecretsContainer(s3GatewayService.retrieveSecrets(programArguments.getS3bucket(),programArguments.getS3bucketObjectMailgun()),false);
            ISecretsContainer authySecretsContainer = new SecretsContainer(s3GatewayService.retrieveSecrets(programArguments.getS3bucket(),programArguments.getS3bucketObjectAuthy()),false);
            ISecretsContainer twilioSecretsContainer = new SecretsContainer(s3GatewayService.retrieveSecrets(programArguments.getS3bucket(),programArguments.getS3bucketObjectTwilio()),true);
            ISecretsContainer keyringSecretsContainer = new SecretsContainer(s3GatewayService.retrieveSecrets(programArguments.getS3bucket(),programArguments.getS3bucketObjectKeyring()),true);
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
