package com.itavery.forecast.credentials;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-19
 * https://github.com/helloavery
 */

public class SecretsContainer implements ISecretsContainer{

    private static final Logger LOGGER = LogManager.getLogger(SecretsContainer.class);

    private String apiKey;
    private String key;
    private String value;

    public SecretsContainer(String secrets, boolean keyValuePair){
        if(keyValuePair){
            try (Scanner scanner = new Scanner(secrets)) {
                key = scanner.nextLine().replace("KEY=","");
                value = scanner.nextLine().replace("VALUE=","");
            } catch (Exception e) {
                LOGGER.error("Could not retrieve credentials from secrets string provided");
                throw new RuntimeException(e.getMessage());
            }
        }
        else{
            apiKey = secrets;
        }
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getkey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
