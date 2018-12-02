package com.itavery.forecast.service.forecast;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  1/30/18
 |
 *===========================================================================*/

import com.itavery.forecast.ForecastConstants;
import com.itavery.forecast.audit.AuditType;
import com.itavery.forecast.dao.forecast.ProductForecastDAO;
import com.itavery.forecast.exceptions.InvalidUserException;
import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;
import com.itavery.forecast.product.ProductType;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.validator.ProductForecastValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductForecastServiceImpl implements ProductForecastService {

    private final static Logger LOGGER = LogManager.getLogger(ProductForecastServiceImpl.class);

    private final AuditService auditService;
    private final ProductForecastDAO productForecastDAO;
    private ProductForecastValidator productForecastValidator;

    public ProductForecastServiceImpl(final AuditService auditService, final ProductForecastDAO productForecastDAO, ProductForecastValidator productForecastValidator) {
        this.auditService = auditService;
        this.productForecastDAO = productForecastDAO;
        this.productForecastValidator = productForecastValidator;
    }

    @Override
    public String addForecastEntry(ProductForecastDTO productForecast, Integer userId) throws ServiceException {
        String returnMessage = null;
        try {
            LOGGER.info("Validating forecast entry for user {}", userId);
            productForecastValidator.validate(productForecast);
            //TODO: Implement Audit Service
            auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ENTRY_ADDED, ProductType.FORECAST);

            LOGGER.info("Attempting to add forecast entry for user {}", userId);
            returnMessage = productForecastDAO.addForecastEntry(userId, productForecast);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not add forecast entry for user {}", userId);
                LOGGER.error(e.getMessage(), e);
            }
        }
        return returnMessage;
    }

    @Override
    public List<ProductForecastDTO> getForecastEntries(Integer userId) throws ServiceException {
        LOGGER.info("Attempting to get forecast entries for user {}", userId);
        List<ProductForecastDTO> productForecastDtoList = productForecastDAO.getForecastEntries(userId);
        if (productForecastDtoList.isEmpty()) {
            throw new InvalidUserException("Service.USER_NOT_FOUND");
        } else {
            return productForecastDtoList;
        }
    }

    @Override
    public String updateForecastEntries(List<ProductForecast> productForecastList, Integer userId) throws ServiceException {
        String returnMessage = null;
        try {
            for (ProductForecast productForecast : productForecastList) {
                LOGGER.info("Validating entries for forecast id {}", productForecast.getProductForecastId());
                productForecastValidator.validate(productForecast);
            }
            LOGGER.info("Attempting to update forecast entries for user{}", userId);
            returnMessage = productForecastDAO.updateForecastEntries(productForecastList, userId);
            //TODO: Implement Audit Service
            auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ENTRY_UPDATED, ProductType.FORECAST);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not update forecast entry for user {}", userId);
                LOGGER.error(e.getMessage(), e);
            }
        }
        return returnMessage;
    }

    @Override
    public String deleteForecastEntry(List<Integer> productForecastId, Integer userId) throws ServiceException {
        String returnMessage = null;
        try {
            LOGGER.info("Attempting to delete forecast entry: " + productForecastId);
            returnMessage = productForecastDAO.deleteForecastEntry(productForecastId);
            //TODO: Implement Audit Service
            auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ENTRY_REMOVED, ProductType.FORECAST);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not delete product forecast entry {}", productForecastId);
                LOGGER.error(e.getMessage(), e);
            }
        }
        return returnMessage;
    }
}
