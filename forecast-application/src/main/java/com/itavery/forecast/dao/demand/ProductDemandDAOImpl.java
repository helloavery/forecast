package com.itavery.forecast.dao.demand;

import com.itavery.forecast.Constants;
import com.itavery.forecast.domain.adaptors.DemandAdaptor;
import com.itavery.forecast.domain.mongodb.MongoDBBase;
import com.itavery.forecast.request.ProductDemandRequest;
import com.itavery.forecast.response.ProductDemandResponse;
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
public class ProductDemandDAOImpl implements ProductDemandDAO {

    private static final Logger LOGGER = LogManager.getLogger(ProductDemandDAOImpl.class);

    private MongoDBBase mongoDBBase;

    @Inject
    public void setMongoDBBase(MongoDBBase mongoDBBase) {
        this.mongoDBBase = mongoDBBase;
    }

    @Override
    public String addDemandEntry(int userId, ProductDemandRequest pdRequest) throws DAOException {
        LOGGER.info("Starting session factory for adding demand entries");
        try {
            DBObject dbObject = DemandAdaptor.toNewDBObject(pdRequest);
            mongoDBBase.insertDocument(Constants.PRODUCT_DEMAND_MONGO_COLLECTION, dbObject);
            return Constants.PRODUCT_DEMAND_ADD_ENTRY_SUCCESS;
        } catch (Exception e) {
            LOGGER.error("Could not add demand entry for userId {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Product Demand DAO Error: Could not add entry");
        }
    }

    @Override
    public ProductDemandResponse getDemandEntry(int productDemandId) throws DAOException {
        LOGGER.info("Starting session factory for retrieving single demand entry for entry {}", productDemandId);
        try {
            return mongoDBBase.getDocumentById(ProductDemandResponse.class, Constants.PRODUCT_DEMAND_MONGO_COLLECTION, productDemandId);
        } catch (Exception e) {
            LOGGER.error("Could not fetch entry for demandId {}", productDemandId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Product Demand DAO Error: Could not get entry");
        }
    }

    @Override
    public List<ProductDemandResponse> getUserDemandEntries(int userId) throws DAOException {
        LOGGER.info("Starting session factory for retrieving demand entries for user {}", userId);
        try {
            return mongoDBBase.getListOfDocumentsByKey(ProductDemandResponse.class, Constants.PRODUCT_DEMAND_MONGO_COLLECTION, "userId", userId);
        } catch (Exception e) {
            LOGGER.error("Could not fetch demand entries for userId {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Product Demand DAO Error: Could not get list of demand entries");
        }
    }


    @Override
    public void updateDemandEntry(List<ProductDemandRequest> pdRequest, int userId) throws DAOException {
        LOGGER.info("Starting session to update demand entry for user: {}", userId);
        try {
            List<ProductDemandRequest> oldProductDemand = mongoDBBase.getListOfDocumentsByKey(ProductDemandRequest.class, Constants.PRODUCT_DEMAND_MONGO_COLLECTION,
                    "userId", userId);
            pdRequest.forEach(request -> oldProductDemand.forEach(oldRequest ->
                    mongoDBBase.updateDocument(Constants.PRODUCT_DEMAND_MONGO_COLLECTION, DemandAdaptor.toDBObject(oldRequest), DemandAdaptor.toDBObject(request))));
        } catch (Exception e) {
            LOGGER.error("Could not update demand entry for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Product Demand DAO Error: Could not update demand entry");
        }
    }

    @Override
    public String removeDemandEntry(List<Integer> productDemandId) throws DAOException {
        LOGGER.info("Starting session to remove demand entries for {}", productDemandId.toString());
        try {
            productDemandId.forEach(id -> mongoDBBase.deleteDocument(Constants.PRODUCT_DEMAND_MONGO_COLLECTION, id));
            return Constants.PRODUCT_FORECAST_REMOVE_ENTRY_SUCCESSFUL;
        } catch (Exception e) {
            LOGGER.error("Could not remove demand entry for demandId {}", productDemandId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,  "Product Demand DAO Error: Could not remove demand entry");
        }
    }
}
