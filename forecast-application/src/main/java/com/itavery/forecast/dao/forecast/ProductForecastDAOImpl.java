package com.itavery.forecast.dao.forecast;

import com.itavery.forecast.Constants;
import com.itavery.forecast.domain.adaptors.ForecastAdaptor;
import com.itavery.forecast.domain.mongodb.MongoDBBase;
import com.itavery.forecast.request.ProductForecastRequest;
import com.itavery.forecast.response.ProductForecastResponse;
import com.itavery.forecast.utils.exceptions.DAOException;
import com.mongodb.DBObject;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-10
 * https://github.com/helloavery
 */

@Repository
public class ProductForecastDAOImpl implements ProductForecastDAO {

    private static final Logger LOGGER = LogManager.getLogger(ProductForecastDAOImpl.class);

    private MongoDBBase mongoDBBase;

    @Inject
    public void setMongoDBBase(MongoDBBase mongoDBBase) {
        this.mongoDBBase = mongoDBBase;
    }

    @Override
    public String addForecastEntry(Integer userId, ProductForecastRequest pfRequest) throws DAOException {
        LOGGER.info("Starting session factory for adding forecast entries");
        try {
            DBObject dbObject = ForecastAdaptor.toNewDBObject(pfRequest);
            mongoDBBase.insertDocument(Constants.PRODUCT_FORECAST_MONGO_COLLECTION, dbObject);
            LOGGER.info("Adding forecast entry for userId {}", userId);
            return Constants.PRODUCT_FORECAST_ADD_ENTRY_SUCCESS;
        } catch (DAOException e) {
            LOGGER.error("Could not add forecast entry/entries for userId {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Product Forecast DAO Error: Could not add entry");
        }
    }

    @Override
    public List<ProductForecastResponse> getForecastEntries(Integer userId) throws DAOException {
        LOGGER.info("Starting session factory for getting forecast entries");
        try {
            return  mongoDBBase.getListOfDocumentsByKey(ProductForecastResponse.class, Constants.PRODUCT_FORECAST_MONGO_COLLECTION, "userId", userId);
        } catch (DAOException e) {
            LOGGER.error("Could not get forecast entries for userId {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Product Forecast DAO Error: Could not get forecast entries");
        }
    }

    @Override
    public String updateForecastEntries(List<ProductForecastRequest> productForecastList, Integer userId) throws DAOException {
        LOGGER.info("Starting session factory for updating forecast entries");
        try {
            List<ProductForecastRequest> oldProductForecast = mongoDBBase.getListOfDocumentsByKey(ProductForecastRequest.class, Constants.PRODUCT_FORECAST_MONGO_COLLECTION,
                    "userId", userId);
            productForecastList.forEach(request -> oldProductForecast.forEach(oldRequest ->
                    mongoDBBase.updateDocument(Constants.PRODUCT_FORECAST_MONGO_COLLECTION, ForecastAdaptor.toDBObject(oldRequest), ForecastAdaptor.toDBObject(request))));
            LOGGER.info("updated demand entries for user {}", userId);
            return Constants.PRODUCT_FORECAST_SUCCESSFUL_UPDATE;
        } catch (DAOException e) {
            LOGGER.error("Could not update forecast entries for forecast id {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Product Forecast DAO Error: Could not update forecast entry");
        }
    }

    @Override
    public String deleteForecastEntry(List<Integer> productForecastId) throws DAOException {
        LOGGER.info("Starting session factory for deleting forecast entries");
        try {
            productForecastId.forEach(id -> mongoDBBase.deleteDocument(Constants.PRODUCT_FORECAST_MONGO_COLLECTION, id));
            return Constants.PRODUCT_FORECAST_REMOVE_ENTRY_SUCCESSFUL;
        } catch (DAOException e) {
            LOGGER.error("Could not delete forecast entry for forecast entry {}", productForecastId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Product Forecast DAO Error: Could not remove delete entry");
        }
    }
}
