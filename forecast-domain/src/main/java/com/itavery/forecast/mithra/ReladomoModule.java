package com.itavery.forecast.mithra;

import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.credentials.SecretsRetrieval;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-09
 * https://github.com/helloavery
 */

@Configuration
public class ReladomoModule {

    private final ProgramArguments programArguments;
    private final SecretsRetrieval secretsRetrieval;

    public ReladomoModule(final ProgramArguments programArguments, final SecretsRetrieval secretsRetrieval){
        this.programArguments = programArguments;
        this.secretsRetrieval = secretsRetrieval;
    }

    @Bean
    ForecastSessionManager forecastSessionManager() {
        ForecastSessionManager forecastSessionManager = new ForecastSessionManager();
        try {
            forecastSessionManager.setEnv(getProperties());
            forecastSessionManager.initReladomo();
            return forecastSessionManager;
        } catch (Exception e) {
            throw new RuntimeException("Could not initiate reladomo runtime context: " + e.getMessage(), e);
        }
    }

    private Properties getProperties(){
        final String DATASOURCE_DRIVER = "forecast.driverClassName";
        final String DATASOURCE_URL = "forecast.datasourceUrl";
        final String DATASOURCE_SCHEMA = "forecast.schema";
        final String DATASOURCE_USERNAME = "forecast.datasourceUsername";
        final String DATASOURCE_PASSWORD = "forecast.datasourcePassword";
        final String DATASOURCE_SHOW_SQL = "showSql";
        final String DATASOURCE_SESSION_CONTEXT = "current_session_context_class";

        Properties properties = new Properties();
        properties.setProperty(DATASOURCE_DRIVER, programArguments.getDriverClass());
        properties.setProperty(DATASOURCE_URL, programArguments.getDataSource());
        properties.setProperty(DATASOURCE_SCHEMA, programArguments.getSchema());
        properties.setProperty(DATASOURCE_USERNAME, secretsRetrieval.getKeyringKey());
        properties.setProperty(DATASOURCE_PASSWORD, secretsRetrieval.getKeyringValue());
        properties.setProperty(DATASOURCE_SHOW_SQL, programArguments.getShowSql());
        properties.setProperty(DATASOURCE_SESSION_CONTEXT, programArguments.getSessionContextClass());
        return properties;
    }
}
