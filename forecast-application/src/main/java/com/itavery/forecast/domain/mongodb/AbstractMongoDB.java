package com.itavery.forecast.domain.mongodb;

import com.averygrimes.nexus.pojo.Constants;
import com.averygrimes.nexus.util.exceptions.MongoDBException;
import com.google.gson.Gson;
import com.itavery.forecast.config.ProgramArguments;
import com.itavery.forecast.domain.util.DataVersioningUtil;
import com.itavery.forecast.utils.credentials.SecretsRetrieval;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 10/7/19
 * https://github.com/helloavery
 */

public abstract class AbstractMongoDB {

    private static final Logger LOGGER = LogManager.getLogger(AbstractMongoDB.class);

    private ProgramArguments programArguments;
    private SecretsRetrieval secretsRetrieval;

    private MongoClient mongoClient;
    private DB mongoDatabase;

    @PostConstruct
    public void init(){
        if(this.mongoClient == null){
            this.mongoClient = this.setupMongoClient();
        }
    }

    private MongoClient setupMongoClient(){
        String host = programArguments.getDatasourceHost();
        int port =  programArguments.getDatasourcePort();
        String databaseName = programArguments.getSchema();
        MongoCredential credential = setMongoCredentials(databaseName);
        try{
            return new MongoClient(new ServerAddress(host,port), Arrays.asList(credential));
        }
        catch(UnknownHostException e){
            throw new MongoDBException("Error creating new MongoClient. Caught error is " + e.getMessage());
        }
    }

    private MongoCredential setMongoCredentials(String database){
        String username = secretsRetrieval.getKeyringKey();
        String pwd = secretsRetrieval.getKeyringValue();
        if(username == null|| pwd == null){
            LOGGER.error("Username and/or password while setting up credentials came back null");
            throw new MongoDBException("Username and/or password while setting up credentials came back null");
        }
        return MongoCredential.createCredential(username, database, pwd.toCharArray());
    }

    private DB getMongoDatabase() {
        if(this.mongoDatabase == null){
            this.mongoDatabase = mongoClient.getDB(programArguments.getSchema());
        }
        return this.mongoDatabase;
    }

    private DBCollection getDBCollection(String collection) {
        return this.getMongoDatabase().getCollection(collection);
    }

    protected <T> T getDocumentById(Class<T> clazz, String collection, Object idValue){
        DBCollection dbCollection = this.getDBCollection(collection);
        DBObject query = new BasicDBObject("_id", idValue);
        DBCursor cursor = dbCollection.find(query);
        DBObject dbObject = cursor.one();
        String JSONOutput = JSON.serialize(new Document(dbObject.toMap()));
        Gson gson = new Gson();
        return gson.fromJson(JSONOutput, clazz);
    }

    protected <T> T getDocumentByKey(Class<T> clazz, String collection, String key, Object value){
        DBCollection dbCollection = this.getDBCollection(collection);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(key, value);
        DBCursor cursor = dbCollection.find(searchQuery);
        DBObject dbObject = cursor.one();
        String JSONOutput = JSON.serialize(new Document(dbObject.toMap()));
        Gson gson = new Gson();
        return gson.fromJson(JSONOutput, clazz);
    }
    protected <T> List<T> getListOfDocumentsByKey(Class<T> clazz, String collection, String key, Object value){
        List<T> documents = new ArrayList<>();
        DBCollection dbCollection = this.getDBCollection(collection);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(key, value);
        Iterator<DBObject> dbObject = dbCollection.find(searchQuery).iterator();
        List<DBObject> dbObjects = new ArrayList<>();
        while(dbObject.hasNext()){
            dbObjects.add(dbObject.next());
        }
        Gson gson = new Gson();
        dbObjects.forEach(result -> documents.add(gson.fromJson(JSON.serialize(new Document(result.toMap())), clazz)));
        return documents;
    }

    protected void insertDocument(String collection, DBObject dbObject){
        DBCollection dbCollection = this.getDBCollection(collection);
        dbCollection.insert(dbObject);
    }

    protected void bulkInsertDocuments(Map<String, List<DBObject>> documentsMap){
        List<DBCollection> dbCollections = new ArrayList<>();
        for(String collection : documentsMap.keySet()){
            DBCollection dbCollection = this.getDBCollection(collection);
            dbCollections.add(dbCollection);
        }
        for(DBCollection dbCollection : dbCollections){
            BulkWriteOperation bulkWriteOperation = dbCollection.initializeUnorderedBulkOperation();
            if(documentsMap.containsKey(dbCollection.getName())){
                documentsMap.get(dbCollection.getName()).forEach(bulkWriteOperation::insert);
            }
            bulkWriteOperation.execute();
        }
    }

    protected void updateDocument(String collection, DBObject oldObject, DBObject newObject){
        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newObject);
        DBCollection dbCollection = this.getDBCollection(collection);
        dbCollection.update(oldObject, updateObject);
        this.createAndInsertHistory(newObject, oldObject);
    }

    protected void createAndInsertHistory(Object currentObject, Object oldObject){
        DBObject dbObject = DataVersioningUtil.createDBObjectHistory(currentObject, oldObject);
        this.insertDocument(Constants.REVISION_HISTORY_MONGO_COLLECTION, dbObject);
    }

    protected void deleteDocument(String collection, Object idValue){
        DBCollection dbCollection = this.getDBCollection(collection);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", idValue);
        dbCollection.remove(searchQuery);
    }

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Inject
    public void setSecretsRetrieval(SecretsRetrieval secretsRetrieval) {
        this.secretsRetrieval = secretsRetrieval;
    }
}
