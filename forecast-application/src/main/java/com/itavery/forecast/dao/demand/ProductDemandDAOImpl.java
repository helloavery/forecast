package com.itavery.forecast.dao.demand;

import com.itavery.forecast.assemblers.DemandAssembler;
import com.itavery.forecast.enums.OperationResult;
import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.mithra.annotation.Transactional;
import com.itavery.forecast.mithra.product.ProductDemandDB;
import com.itavery.forecast.mithra.product.ProductDemandDBFinder;
import com.itavery.forecast.mithra.product.ProductDemandDBList;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-10
 * https://github.com/helloavery
 */

@Repository
public class ProductDemandDAOImpl implements ProductDemandDAO {

    private static final Logger LOGGER = LogManager.getLogger(ProductDemandDAOImpl.class);
    @Inject
    private DemandAssembler demandAssembler;

    @Override
    @Transactional
    public String addDemandEntry(Integer userId, ProductDemandDTO productDemandDTO) throws DAOException {
        LOGGER.info("Starting session factory for adding demand entries");
        try {
            ProductDemandDB productDemand = demandAssembler.covertToDB(productDemandDTO);
            productDemand.setUserId(userId);
            productDemand.cascadeInsert();
            return OperationResult.PRODUCT_DEMAND_ADD_ENTRY_SUCCESS.getMessage();
        } catch (DAOException e) {
            LOGGER.error("Could not add demand entry for userId {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Demand DAO Error: Could not add entry");
        }
    }

    @Override
    public ProductDemandDTO getDemandEntry(Integer productDemandId) throws DAOException {
        LOGGER.info("Starting session factory for retrieving single demand entry for entry {}", productDemandId);
        ProductDemandDTO productDemandDto = null;
        try {
            ProductDemandDB productDemandEntry = fetchProductDemandByDemandId(productDemandId);
            LOGGER.info("Retrieved demand entry for demand entry id {}", productDemandId);
            if (productDemandEntry != null) {
                productDemandDto = demandAssembler.covertToDTO(productDemandEntry);
                LOGGER.info("Demand entry retrieved for id {}", productDemandId);
            } else {
                LOGGER.error("Demand entry not found based on userId");
            }
        } catch (DAOException e) {
            LOGGER.error("Could not fetch entry for demandId {}", productDemandId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Demand DAO Error: Could not get entry");
        }
        return productDemandDto;
    }

    @Override
    public List<ProductDemandDTO> getUserDemandEntries(Integer userId) throws DAOException {
        List<ProductDemandDTO> productDemandDtoList = new ArrayList<>();
        LOGGER.info("Starting session factory for retrieving demand entries for user {}", userId);
        try {
            LOGGER.info("Retrieving list of demand entries");
            ProductDemandDBList productDemandDBList = fetchProductDemandListByUserId(userId);
            if (!productDemandDBList.isEmpty()) {
                for (ProductDemandDB productDemandDB : productDemandDBList) {
                    ProductDemandDTO productDemandDTO = demandAssembler.covertToDTO(productDemandDB);
                    productDemandDtoList.add(productDemandDTO);
                    LOGGER.info("Retrieved demand entry for demand id {}", productDemandDB.getProductDemandId());
                }
            } else {
                LOGGER.error("User id not found so could not retrieve demand entries");
            }
        } catch (DAOException e) {
            LOGGER.error("Could not fetch demand entries for userId {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Demand DAO Error: Could not get list of demand entries");
        }
        return productDemandDtoList;
    }

    @Override
    @Transactional
    public void updateDemandEntry(List<ProductDemand> productDemandList, Integer userId) throws DAOException {
        LOGGER.info("Starting session to update demand entry for user: {}", userId);
        try {
            for (ProductDemand productDemand : productDemandList) {
                ProductDemandDB productDemandDB = fetchProductDemandByIds(productDemand.getProductDemandId(), userId);
                productDemandDB.setEntryDate(productDemand.getEntryDate());
                productDemandDB.setProductCode(productDemand.getProductCode());
                productDemandDB.setWarehouse(productDemand.getWarehouse());
                productDemandDB.setProductCategory(productDemand.getProductCategory());
                productDemandDB.setOrderDemand(productDemand.getOrderDemand());
                productDemandDB.setUserId(productDemand.getUserId());
                LOGGER.info("updated demand entries for user {} and demand entry {}", userId, productDemand.getProductDemandId());
            }
        } catch (DAOException e) {
            LOGGER.error("Could not update demand entry for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Demand DAO Error: Could not update demand entry");
        }
    }

    @Override
    @Transactional
    public String removeDemandEntry(List<Integer> productDemandId) throws DAOException {
        LOGGER.info("Starting session to remove demand entry");
        String message = null;
        try {
            LOGGER.info("Demand entries to be removed {}", productDemandId);
            for (Integer demandId : productDemandId) {
                ProductDemandDB productDemandDB = fetchProductDemandByDemandId(demandId);
                if (productDemandDB != null) {
                    LOGGER.info("Removing entry: " + demandId);
                    productDemandDB.terminate();
                    message = OperationResult.PRODUCT_FORECAST_REMOVE_ENTRY_SUCCESSFUL.getMessage();

                } else {
                    LOGGER.error("No entry found for id: {}, continuing operations...", demandId);
                }
            }
        } catch (DAOException e) {
            LOGGER.error("Could not remove demand entry for demandId {}", productDemandId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Demand DAO Error: Could not remove demand entry");
        }
        return message;
    }

    private ProductDemandDB fetchProductDemandByDemandId(Integer productDemandId) {
        return ProductDemandDBFinder.findOne(ProductDemandDBFinder.productDemandId().eq(productDemandId));
    }

    private ProductDemandDB fetchProductDemandByIds(Integer productDemandId, Integer userId) {
        return ProductDemandDBFinder.findOne(ProductDemandDBFinder.productDemandId().eq(productDemandId)
                .and(ProductDemandDBFinder.userId().eq(userId)));
    }

    private ProductDemandDBList fetchProductDemandListByUserId(Integer userId) {
        return ProductDemandDBFinder.findMany(ProductDemandDBFinder.userId().eq(userId));
    }
}
