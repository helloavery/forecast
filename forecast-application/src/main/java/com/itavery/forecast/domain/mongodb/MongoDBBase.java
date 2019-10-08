package com.itavery.forecast.domain.mongodb;

import com.mongodb.DBObject;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 10/7/19
 * https://github.com/helloavery
 */

@Named
public class MongoDBBase extends AbstractMongoDB {

    public <T> T getDocumentById(Class<T> clazz, String collection, Object idValue){
        return super.getDocumentById(clazz, collection, idValue);
    }

    public <T> T getDocumentByKey(Class<T> clazz, String collection, String key, Object value){
        return super.getDocumentByKey(clazz, collection, key, value);
    }

    public <T> List<T> getListOfDocumentsByKey(Class<T> clazz, String collection, String key, Object value){
        return super.getListOfDocumentsByKey(clazz, collection, key, value);
    }

    public void insertDocument(String collection, DBObject dbObject){
        super.insertDocument(collection, dbObject);
    }

    public void bulkInsertDocuments(Map<String, List<DBObject>> documentsMap){
        super.bulkInsertDocuments(documentsMap);
    }

    public void updateDocument(String collection, DBObject oldObject, DBObject newObject){
        super.updateDocument(collection, oldObject, newObject);
    }

    public void createAndInsertHistory(Object currentObject, Object oldObject){
        super.createAndInsertHistory(currentObject, oldObject);
    }

    public void deleteDocument(String collection, Object idValue){
        super.deleteDocument(collection, idValue);
    }
}
