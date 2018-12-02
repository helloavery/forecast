package com.itavery.forecast.launch;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  3/31/18
 |
 *===========================================================================*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication(scanBasePackages = "com.itavery.forecast")
@ComponentScan(basePackages = "com.itavery.forecast")
@EntityScan(basePackages = "com.itavery.forecast")
public class ForecastLauncher implements ApplicationRunner {

    private static final Logger LOGGER = LogManager.getLogger(ForecastLauncher.class);

    private final List<String> startupParams = Arrays.asList("datasource", "schema", "filename", "hibernateDdlAuto", "environment");

    public static void main(String[] args) {
        SpringApplication.run(ForecastLauncher.class, args);
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
