package com.itavery.forecast;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-03-31
 * https://github.com/helloavery
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.itavery.forecast", "com.averygrimes"})
public class SpringBootInitializer implements ApplicationRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootInitializer.class);

    private final List<String> startupParams = Arrays.asList("datasourcehost", "datasourceport", "schema", "s3bucket", "s3bucketObjectKeyring",
            "s3bucketObjectMailgun", "s3bucketObjectAuthy", "s3bucketObjectTwilio", "authyClientEndpoint", "mailgunClientEndpoint",
            "kafkaBootstrapAddress", "nexus.kafka.env", "discovery.environment");

    public static void main(String[] args) {
        SpringApplication.run(SpringBootInitializer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        startupParams.forEach(param -> {
            if (!args.containsOption(param)) {
                throw new RuntimeException("Application boot failed, missing required param: " + param);
            }
        });
        for (String name : args.getOptionNames()) {
            LOGGER.info("arg-" + name + "=" + args.getOptionValues(name));
        }
    }
}
