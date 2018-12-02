package com.itavery.forecast.mithra;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  10/9/18            
 |            
 *===========================================================================*/

import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.credentials.CredentialObtainer;
import com.itavery.forecast.credentials.Credentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class ReladomoModule {

    private final ProgramArguments programArguments;

    public ReladomoModule(final ProgramArguments programArguments){
        this.programArguments = programArguments;
    }

    private String DATASOURCE_DRIVER = "forecast.driverClassName";
    private String DATASOURCE_URL = "forecast.datasourceUrl";
    private String DATASOURCE_SCHEMA = "forecast.schema";
    private String DATASOURCE_USERNAME = "forecast.datasourceUsername";
    private String DATASOURCE_PASSWORD = "forecast.datasourcePassword";
    private String DATASOURCE_SHOW_SQL = "showSql";
    private String DATASOURCE_DDL_AUTO = "hbm2ddlAuto";
    private String DATASOURCE_SESSION_CONTEXT = "current_session_context_class";

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

    private Properties getProperties() {
        final String datasourceDriver = DATASOURCE_DRIVER;
        final String datasourceUrl = DATASOURCE_URL;
        final String datasourceSchema = DATASOURCE_SCHEMA;
        final String datasourceUsername = DATASOURCE_USERNAME;
        final String datasourcePassword = DATASOURCE_PASSWORD;
        final String datasourceShowSql = DATASOURCE_SHOW_SQL;
        final String datasourceDdlAuto = DATASOURCE_DDL_AUTO;
        final String datasourceSessionContext = DATASOURCE_SESSION_CONTEXT;

        CredentialObtainer credentialObtainer = new Credentials(programArguments.getFilename());
        Properties properties = new Properties();
        properties.setProperty(datasourceDriver, programArguments.getDriverClass());
        properties.setProperty(datasourceUrl, programArguments.getDataSource());
        properties.setProperty(datasourceSchema, programArguments.getSchema());
        properties.setProperty(datasourceUsername, credentialObtainer.getKey());
        properties.setProperty(datasourcePassword, credentialObtainer.getValue());
        properties.setProperty(datasourceShowSql, programArguments.getShowSql());
        properties.setProperty(datasourceDdlAuto, programArguments.getHibernateDdlAuto());
        properties.setProperty(datasourceSessionContext, programArguments.getSessionContextClass());
        return properties;
    }
}
