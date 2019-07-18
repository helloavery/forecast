package com.itavery.forecast.domain.mithra;

import com.gs.fw.common.mithra.MithraManager;
import com.gs.fw.common.mithra.MithraManagerProvider;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-09
 * https://github.com/helloavery
 */

public class ForecastSessionManager {

    private Properties env;

    public void setEnv(Properties env){
        this.env = env;
        ForecastConnectionConfig.setConnectionProperties(env);
    }

    public void initReladomo() throws Exception {
        MithraManager mithraManager = MithraManagerProvider.getMithraManager();
        mithraManager.setTransactionTimeout(60 * 1000);
        InputStream stream = loadReladomoXMLFromClasspath("domain/config/ReladomoRuntimeConfig.xml");
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
