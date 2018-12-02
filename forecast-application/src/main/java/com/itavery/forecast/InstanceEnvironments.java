package com.itavery.forecast;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/21/18            
 |            
 *===========================================================================*/

import com.itavery.forecast.mithra.organization.InstanceEnvironmentsDB;
import com.itavery.forecast.mithra.organization.InstanceEnvironmentsDBFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
