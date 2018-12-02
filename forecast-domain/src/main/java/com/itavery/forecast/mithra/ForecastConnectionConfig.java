package com.itavery.forecast.mithra;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  10/3/18            
 |            
 *===========================================================================*/

import com.gs.fw.common.mithra.bulkloader.BulkLoader;
import com.gs.fw.common.mithra.bulkloader.BulkLoaderException;
import com.gs.fw.common.mithra.connectionmanager.SourcelessConnectionManager;
import com.gs.fw.common.mithra.connectionmanager.XAConnectionManager;
import com.gs.fw.common.mithra.databasetype.DatabaseType;
import com.gs.fw.common.mithra.databasetype.PostgresDatabaseType;

import java.sql.Connection;
import java.util.Properties;
import java.util.TimeZone;

public class ForecastConnectionConfig implements SourcelessConnectionManager {

    private static final String MAX_POOL_SIZE_KEY = "maxPoolSize";
    private final static int DEFAULT_MAX_WAIT = 500;
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final TimeZone NEW_YORK_TIMEZONE = TimeZone.getTimeZone("America/New_York");
    private static ForecastConnectionConfig instance;
    private final String connectionString = "jdbc:postgresql://";
    private static final String DRIVER_CLASS_NAME = "forecast.driverClassName";
    private static final String JDBC_URL = "forecast.datasourceUrl";
    private static final String JDBC_SCHEMA = "forecast.schema";
    private static final String JDBC_USERNAME = "forecast.datasourceUsername";
    private static final String JDBC_PASSWORD = "forecast.datasourcePassword";
    private static Properties connectionProperties;

    private XAConnectionManager xaConnectionManager;

    private ForecastConnectionConfig() {
        this.createConnectionManager();
    }

    public static synchronized ForecastConnectionConfig getInstance() {
        if (instance == null) {
            instance = new ForecastConnectionConfig();
        }
        return instance;
    }

    public static Properties setConnectionProperties(Properties connectionProperties){
        ForecastConnectionConfig.connectionProperties = connectionProperties;
        return connectionProperties;
    }

    private XAConnectionManager createConnectionManager() {
        xaConnectionManager = new XAConnectionManager();
        xaConnectionManager.setDriverClassName(connectionProperties.getProperty(DRIVER_CLASS_NAME));
        xaConnectionManager.setMaxWait(DEFAULT_MAX_WAIT);
        xaConnectionManager.setJdbcConnectionString(connectionString + connectionProperties.getProperty(JDBC_URL));
        xaConnectionManager.setPort(5432);
        xaConnectionManager.setJdbcUser(connectionProperties.getProperty(JDBC_USERNAME,""));
        xaConnectionManager.setJdbcPassword(connectionProperties.getProperty(JDBC_PASSWORD, ""));
        xaConnectionManager.setDefaultSchemaName(connectionProperties.getProperty(JDBC_SCHEMA));
        xaConnectionManager.setPoolName("forecaster connection pool");
        xaConnectionManager.setInitialSize(1);
        xaConnectionManager.setPoolSize(DEFAULT_POOL_SIZE);
        xaConnectionManager.initialisePool();
        return xaConnectionManager;
    }


    public Connection getConnection() {
        return xaConnectionManager.getConnection();
    }

    public DatabaseType getDatabaseType() {
        return PostgresDatabaseType.getInstance();
    }

    public TimeZone getDatabaseTimeZone() {
        return NEW_YORK_TIMEZONE;
    }

    public BulkLoader createBulkLoader() throws BulkLoaderException {
        throw new RuntimeException("BulkLoader is not supported");
    }

    public String getDatabaseIdentifier() {

        return xaConnectionManager.getHostName() + ':' + xaConnectionManager.getPort();
    }

}
