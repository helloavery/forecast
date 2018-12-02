package com.itavery.forecast.credentials;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  8/25/18            
 |            
 *===========================================================================*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Credentials implements CredentialObtainer {

    private static final Logger LOGGER = LogManager.getLogger(Credentials.class);

    private static final String KEY = "KEY=";
    private static final String VALUE = "VALUE=";

    private String key;
    private String value;

    public Credentials(String filename) {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            List<String> credentials = stream.collect(Collectors.toList());
            Optional<String> keyFromFile = credentials.stream().filter(key -> key.contains(KEY)).findFirst();
            keyFromFile.ifPresent(returnedKey -> key = returnedKey.replace(KEY, ""));
            Optional<String> valueFromFile = credentials.stream().filter(value -> value.contains(VALUE)).findFirst();
            valueFromFile.ifPresent(returnedValue -> value = returnedValue.replace(VALUE, ""));
        } catch (IOException e) {
            LOGGER.error("Could not retrieve credentials from file provided: " + filename);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
