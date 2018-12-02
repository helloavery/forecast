package com.itavery.forecast.mithra;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  10/9/18            
 |            
 *===========================================================================*/

import com.gs.fw.common.mithra.MithraManager;
import com.gs.fw.common.mithra.MithraManagerProvider;

import java.io.InputStream;
import java.util.Properties;

public class ForecastSessionManager {

    private Properties env;

    public void setEnv(Properties env){
        this.env = env;
        ForecastConnectionConfig.setConnectionProperties(env);
    }

    public void initReladomo() throws Exception {
        MithraManager mithraManager = MithraManagerProvider.getMithraManager();
        mithraManager.setTransactionTimeout(60 * 1000);
        InputStream stream = loadReladomoXMLFromClasspath("config/ReladomoRuntimeConfig.xml");
        MithraManagerProvider.getMithraManager().readConfiguration(stream);
        stream.close();
    }

    private InputStream loadReladomoXMLFromClasspath(String fileName) throws Exception {
        InputStream stream = ForecastSessionManager.class.getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            throw new Exception("Failed to locate " + fileName + " in classpath");
        }
        return stream;
    }
}
