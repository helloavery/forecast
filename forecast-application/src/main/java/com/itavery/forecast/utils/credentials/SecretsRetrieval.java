package com.itavery.forecast.utils.credentials;

import com.averygrimes.nexus.credentials.NexusSecretsRetrieval;
import com.averygrimes.nexus.pojo.S3BucketMap;
import com.averygrimes.nexus.pojo.SecretCategory;
import com.itavery.forecast.config.ProgramArguments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-19
 * https://github.com/helloavery
 */

@Component
public class SecretsRetrieval {

    private static final Logger LOGGER = LogManager.getLogger(SecretsRetrieval.class);
    private ProgramArguments programArguments;
    private NexusSecretsRetrieval nexusSecretsRetrieval;

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Inject
    public void setNexusSecretsRetrieval(NexusSecretsRetrieval nexusSecretsRetrieval) {
        this.nexusSecretsRetrieval = nexusSecretsRetrieval;
    }

    private static String mailgunApiKey;
    private static String authyApiKey;
    private static String twilioSid;
    private static String twilioToken;
    private static String keyringKey;
    private static String keyringValue;

    @PostConstruct
    public void init(){
        Map<SecretCategory, S3BucketMap> secretCategoryMap = new HashMap<>();
        secretCategoryMap.put(SecretCategory.MAILGUN, new S3BucketMap(programArguments.getS3bucket(),programArguments.getS3bucketObjectMailgun()));
        secretCategoryMap.put(SecretCategory.AUTHY, new S3BucketMap(programArguments.getS3bucket(),programArguments.getS3bucketObjectAuthy()));
        secretCategoryMap.put(SecretCategory.TWILIO, new S3BucketMap(programArguments.getS3bucket(),programArguments.getS3bucketObjectTwilio()));
        secretCategoryMap.put(SecretCategory.KEYRING, new S3BucketMap(programArguments.getS3bucket(),programArguments.getS3bucketObjectKeyring()));
        Map<SecretCategory, String> secretsMap = nexusSecretsRetrieval.getApplicationSecrets(secretCategoryMap);
        mailgunApiKey = secretsMap.get(SecretCategory.MAILGUN);
        authyApiKey = secretsMap.get(SecretCategory.AUTHY);
        twilioSid = getKeyValuePair(secretsMap.get(SecretCategory.TWILIO), KeyValue.KEY);
        twilioToken = getKeyValuePair(secretsMap.get(SecretCategory.TWILIO), KeyValue.VALUE);
        keyringKey = getKeyValuePair(secretsMap.get(SecretCategory.KEYRING), KeyValue.KEY);
        keyringValue = getKeyValuePair(secretsMap.get(SecretCategory.KEYRING), KeyValue.VALUE);
    }

    private String getKeyValuePair(String keyValueString, KeyValue keyValue){
        try (Scanner scanner = new Scanner(keyValueString)) {
            String key = scanner.nextLine().replace("KEY=","");
            String value = scanner.nextLine().replace("VALUE=","");
            return keyValue == KeyValue.KEY ? key : value;
        } catch (Exception e) {
            LOGGER.error("Could not retrieve credentials from secrets string provided");
            throw new RuntimeException(e.getMessage());
        }
    }

    private enum KeyValue{
        KEY, VALUE
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
