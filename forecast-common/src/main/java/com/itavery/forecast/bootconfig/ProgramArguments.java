package com.itavery.forecast.bootconfig;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-08-25
 * https://github.com/helloavery
 */

public class ProgramArguments {

    private String driverClass = "org.postgresql.Driver";
    private String dataSource;
    private String schema;
    private String s3gatewayendpoint;
    private String s3bucket;
    private String s3bucketObjectKeyring;
    private String s3bucketObjectMailgun;
    private String s3bucketObjectAuthy;
    private String s3bucketObjectTwilio;
    private String s3bucketObjectUploadFile;
    private String showSql = "true";
    private String sessionContextClass = "thread";
    private String environment;
    private String authyClientBasePath;
    private String mailgunClientBasePath;
    private String S3GatewayBasePath;

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

    public String getS3gatewayendpoint() {
        return s3gatewayendpoint;
    }

    public void setS3gatewayendpoint(String s3gatewayendpoint) {
        this.s3gatewayendpoint = s3gatewayendpoint;
    }

    public String getS3bucket() {
        return s3bucket;
    }

    public void setS3bucket(String s3bucket) {
        this.s3bucket = s3bucket;
    }

    public String getS3bucketObjectKeyring() {
        return s3bucketObjectKeyring;
    }

    public void setS3bucketObjectKeyring(String s3bucketObjectKeyring) {
        this.s3bucketObjectKeyring = s3bucketObjectKeyring;
    }

    public String getS3bucketObjectMailgun() {
        return s3bucketObjectMailgun;
    }

    public void setS3bucketObjectMailgun(String s3bucketObjectMailgun) {
        this.s3bucketObjectMailgun = s3bucketObjectMailgun;
    }

    public String getS3bucketObjectAuthy() {
        return s3bucketObjectAuthy;
    }

    public void setS3bucketObjectAuthy(String s3bucketObjectAuthy) {
        this.s3bucketObjectAuthy = s3bucketObjectAuthy;
    }

    public String getS3bucketObjectTwilio() {
        return s3bucketObjectTwilio;
    }

    public void setS3bucketObjectTwilio(String s3bucketObjectTwilio) {
        this.s3bucketObjectTwilio = s3bucketObjectTwilio;
    }

    public String getS3bucketObjectUploadFile() {
        return s3bucketObjectUploadFile;
    }

    public void setS3bucketObjectUploadFile(String s3bucketObjectUploadFile) {
        this.s3bucketObjectUploadFile = s3bucketObjectUploadFile;
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

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getAuthyClientBasePath() {
        return authyClientBasePath;
    }

    public void setAuthyClientBasePath(String authyClientBasePath) {
        this.authyClientBasePath = authyClientBasePath;
    }

    public String getMailgunClientBasePath() {
        return mailgunClientBasePath;
    }

    public void setMailgunClientBasePath(String mailgunClientBasePath) {
        this.mailgunClientBasePath = mailgunClientBasePath;
    }

    public String getS3GatewayBasePath() {
        return S3GatewayBasePath;
    }

    public void setS3GatewayBasePath(String s3GatewayBasePath) {
        S3GatewayBasePath = s3GatewayBasePath;
    }
}
