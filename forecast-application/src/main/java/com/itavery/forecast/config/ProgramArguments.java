package com.itavery.forecast.config;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-08-25
 * https://github.com/helloavery
 */

public class ProgramArguments {

    private String instance;
    private String datasourceHost;
    private int datasourcePort;
    private String schema;
    private String s3bucket;
    private String s3bucketObjectKeyring;
    private String s3bucketObjectMailgun;
    private String s3bucketObjectAuthy;
    private String s3bucketObjectTwilio;
    private String s3bucketObjectUploadFile;
    private String showSql = "true";
    private String sessionContextClass = "thread";
    private String authyClientEndpoint;
    private String mailgunClientEndpoint;
    private String kafkaBootstrapAddress;

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getDatasourceHost() {
        return datasourceHost;
    }

    public void setDatasourceHost(String datasourceHost) {
        this.datasourceHost = datasourceHost;
    }

    public int getDatasourcePort() {
        return datasourcePort;
    }

    public void setDatasourcePort(int datasourcePort) {
        this.datasourcePort = datasourcePort;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
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

    public String getAuthyClientEndpoint() {
        return authyClientEndpoint;
    }

    public void setAuthyClientEndpoint(String authyClientEndpoint) {
        this.authyClientEndpoint = authyClientEndpoint;
    }

    public String getMailgunClientEndpoint() {
        return mailgunClientEndpoint;
    }

    public void setMailgunClientEndpoint(String mailgunClientEndpoint) {
        this.mailgunClientEndpoint = mailgunClientEndpoint;
    }

    public String getKafkaBootstrapAddress() {
        return kafkaBootstrapAddress;
    }

    public void setKafkaBootstrapAddress(String kafkaBootstrapAddress) {
        this.kafkaBootstrapAddress = kafkaBootstrapAddress;
    }
}
