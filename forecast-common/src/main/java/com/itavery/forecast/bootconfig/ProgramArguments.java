package com.itavery.forecast.bootconfig;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  8/25/18            
 |            
 *===========================================================================*/

public class ProgramArguments {

    private String driverClass = "org.postgresql.Driver";
    private String dataSource;
    private String schema;
    private String hibernateDdlAuto;
    private String showSql = "true";
    private String sessionContextClass = "thread";
    private String filename;
    private String environment;

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getHibernateDdlAuto() {
        return hibernateDdlAuto;
    }

    public void setHibernateDdlAuto(String hibernateDdlAuto) {
        this.hibernateDdlAuto = hibernateDdlAuto;
    }

    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }

    public String getSessionContextClass() {
        return sessionContextClass;
    }

    public void setSessionContextClass(String sessionContextClass) {
        this.sessionContextClass = sessionContextClass;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
