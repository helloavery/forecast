package com.itavery.forecast.util;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  7/22/18            
 |            
 *===========================================================================*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SerializeUserMain {

    private static final Logger LOGGER = LogManager.getLogger(SerializeUserMain.class);

    private SerializeUser serializeUser;

    public static void main(String[] args) {
        SerializeUserMain serializeUserMain = new SerializeUserMain();
        serializeUserMain.execute();
    }

    private void execute() {
        try {
            serializeUser.serialize(null, null, null, null, null, null, null, null);
        } catch (Exception e) {
            LOGGER.error("Could not execute serialize user", e.getMessage());
        }
    }
}
