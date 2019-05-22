package com.itavery.forecast;

import com.itavery.forecast.mithra.organization.InstanceEnvironmentsDB;
import com.itavery.forecast.mithra.organization.InstanceEnvironmentsDBFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-21
 * https://github.com/helloavery
 */

public class InstanceEnvironments {

    private static final Logger LOGGER = LogManager.getLogger(InstanceEnvironments.class);

    public static String getInstance(String environment){
        String instanceURL;
        try{
            InstanceEnvironmentsDB instanceEnvironmentsDB = InstanceEnvironmentsDBFinder.findOne(InstanceEnvironmentsDBFinder.environmentKey().eq(environment.toUpperCase()));
            if(instanceEnvironmentsDB == null){
                LOGGER.error("Could not find environment for {}", environment);
                throw new RuntimeException("Could not find environment for: " + environment);
            }
            instanceURL = instanceEnvironmentsDB.getEnvironmentURL();
        }
        catch(Exception e){
            LOGGER.error("Error fetching environment");
            throw new RuntimeException("Error fetching environment", e);
        }
        return instanceURL;
    }
}
