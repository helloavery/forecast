package com.itavery.forecast.dao.forecast;

import com.itavery.forecast.OperationResult;
import com.itavery.forecast.assemblers.ForecastAssembler;
import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.mithra.annotation.Transactional;
import com.itavery.forecast.mithra.product.ProductForecastDB;
import com.itavery.forecast.mithra.product.ProductForecastDBFinder;
import com.itavery.forecast.mithra.product.ProductForecastDBList;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;
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
public class ProductForecastDAOImpl implements ProductForecastDAO {

    private static final Logger LOGGER = LogManager.getLogger(ProductForecastDAOImpl.class);
    @Inject
    private ForecastAssembler forecastAssembler;

    @Override
    @Transactional
    public String addForecastEntry(Integer userId, ProductForecastDTO productForecastDTO) throws DAOException {
        LOGGER.info("Starting session factory for adding forecast entries");
        try {
            ProductForecastDB productForecastDB = forecastAssembler.covertToProductForecastDB(productForecastDTO);
            LOGGER.info("Adding forecast entry for userId {}", userId);
            productForecastDB.setUserId(userId);
            productForecastDB.cascadeInsert();
            return OperationResult.PRODUCT_FORECAST_ADD_ENTRY_SUCCESS.getMessage();
        } catch (DAOException e) {
            LOGGER.error("Could not add forecast entry/entries for userId {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Forecast DAO Error: Could not add entry");
        }
    }

    @Override
    public List<ProductForecastDTO> getForecastEntries(Integer userId) throws DAOException {
        List<ProductForecastDTO> productForecastDtoList = new ArrayList<>();
        LOGGER.info("Starting session factory for getting forecast entries");
        try {
            LOGGER.info("Retrieving list of forecast entries");
            ProductForecastDBList productForecastDBList = fetchProductForecastListByUserId(userId);
            if (!productForecastDBList.isEmpty()) {
                for (ProductForecastDB productForecastDB : productForecastDBList) {
                    ProductForecastDTO productForecastDto = forecastAssembler.covertProductForecastToDTO(productForecastDB);
                    productForecastDtoList.add(productForecastDto);
                }
            } else {
                LOGGER.error("Could not retrieve product forecast entries, user not found for {}", userId);
            }
        } catch (DAOException e) {
            LOGGER.error("Could not get forecast entries for userId {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Forecast DAO Error: Could not get forecast entries");
        }
        return productForecastDtoList;
    }

    @Override
    @Transactional
    public String updateForecastEntries(List<ProductForecast> productForecastList, Integer userId) throws DAOException {
        LOGGER.info("Starting session factory for updating forecast entries");
        String returnMessage;
        try {
            for (ProductForecast productForecast : productForecastList) {
                ProductForecastDB productForecastDB = fetchProductForecastByIds(productForecast.getProductForecastId(), userId);
                productForecastDB.setSku(productForecast.getSku());
                productForecastDB.setNationalInventory(productForecast.getNationalInventory());
                productForecastDB.setLeadTime(productForecast.getLeadTime());
                productForecastDB.setInTransitQty(productForecast.getInTransitQty());
                productForecastDB.setForecastThreeMonths(productForecast.getForecastThreeMonths());
                productForecastDB.setForecastSixMonths(productForecast.getForecastSixMonths());
                productForecastDB.setForecastNineMonths(productForecast.getForecastNineMonths());
                productForecastDB.setSalesOneMonth(productForecast.getSalesOneMonth());
                productForecastDB.setSalesThreeMonths(productForecast.getSalesThreeMonths());
                productForecastDB.setSalesSixMonths(productForecast.getSalesSixMonths());
                productForecastDB.setSalesNineMonths(productForecast.getSalesNineMonths());
                productForecastDB.setMinBank(productForecast.getMinBank());
                productForecastDB.setPotentialIssues(productForecast.getPotentialIssues());
                productForecastDB.setPiecesPastDue(productForecast.getPiecesPastDue());
                productForecastDB.setPerfSixMonthAvg(productForecast.getPerfSixMonthAvg());
                productForecastDB.setPerfTwelveMonthAvg(productForecast.getPerfTwelveMonthAvg());
                productForecastDB.setLocalBoQty(productForecast.getLocalBoQty());
                productForecastDB.setDeckRisk(productForecast.getDeckRisk());
                productForecastDB.setOeConstraint(productForecast.getOeConstraint());
                productForecastDB.setPpapRisk(productForecast.getPpapRisk());
                productForecastDB.setStopAutoBuy(productForecast.getStopAutoBuy());
                productForecastDB.setRevStop(productForecast.getRevStop());
                productForecastDB.setBackorder(productForecast.getBackorder());
                productForecastDB.setUserId(userId);
                LOGGER.info("Updated forecast entry {}", productForecast.getProductForecastId());
            }
            returnMessage = OperationResult.PRODUCT_FORECAST_SUCCESSFUL_UPDATE.getMessage();
            LOGGER.info("updated demand entries for user {}", userId);
        } catch (DAOException e) {
            LOGGER.error("Could not update forecast entries for forecast id {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Forecast DAO Error: Could not update forecast entry");
        }
        return returnMessage;
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public String deleteForecastEntry(List<Integer> productForecastId) throws DAOException {
        LOGGER.info("Starting session factory for deleting forecast entries");
        String returnMessage;
        try {
            for (Integer entryToBeRemoved : productForecastId) {
                ProductForecastDB productForecastDB = fetchProductForecastByForecastId(entryToBeRemoved);
                if (productForecastDB != null) {
                    LOGGER.info("Demand entries to be removed {}", entryToBeRemoved);
                    productForecastDB.terminate();
                }
            }
            returnMessage = OperationResult.PRODUCT_FORECAST_REMOVE_ENTRY_SUCCESSFUL.getMessage();
        } catch (DAOException e) {
            LOGGER.error("Could not delete forecast entry for forecast entry {}", productForecastId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Product Forecast DAO Error: Could not remove delete entry");
        }
        return returnMessage;
    }

    private ProductForecastDB fetchProductForecastByForecastId(Integer productForecastId) {
        return ProductForecastDBFinder.findOne(ProductForecastDBFinder.productForecastId().eq(productForecastId));
    }

    private ProductForecastDB fetchProductForecastByIds(Integer productForecastId, Integer userId) {
        return ProductForecastDBFinder.findOne(ProductForecastDBFinder.productForecastId().eq(productForecastId)
                .and(ProductForecastDBFinder.userId().eq(userId)));
    }

    private ProductForecastDBList fetchProductForecastListByUserId(Integer userId) {
        return ProductForecastDBFinder.findMany(ProductForecastDBFinder.userId().eq(userId));
    }
}
