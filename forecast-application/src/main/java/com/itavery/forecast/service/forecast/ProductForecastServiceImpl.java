package com.itavery.forecast.service.forecast;

import com.itavery.forecast.ResponseBuilder;
import com.itavery.forecast.constants.AuditType;
import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.constants.ProductType;
import com.itavery.forecast.dao.forecast.ProductForecastDAO;
import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.validator.ProductForecastValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-30
 * https://github.com/helloavery
 */

@Service
public class ProductForecastServiceImpl implements ProductForecastService {

    private final static Logger LOGGER = LogManager.getLogger(ProductForecastServiceImpl.class);
    @Inject
    private AuditService auditService;
    @Inject
    private ProductForecastDAO productForecastDAO;
    @Inject
    private ProductForecastValidator productForecastValidator;
    @Inject
    ResponseBuilder responseBuilder;

    @Override
    public Response addForecastEntry(ProductForecastDTO productForecast, Integer userId) throws ServiceException {
        String returnMessage = null;
        try {
            LOGGER.info("Validating forecast entry for user {}", userId);
            productForecastValidator.validate(productForecast);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_ADDED, ProductType.FORECAST);

            LOGGER.info("Attempting to add forecast entry for user {}", userId);
            returnMessage = productForecastDAO.addForecastEntry(userId, productForecast);
            return responseBuilder.createSuccessResponse(returnMessage);
        } catch (Exception e) {
            LOGGER.error("Could not add forecast entry for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            return responseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_ADDING_ENTRY);
        }
    }

    @Override
    public Response getForecastEntries(Integer userId) throws ServiceException {
        LOGGER.info("Attempting to get forecast entries for user {}", userId);
        List<ProductForecastDTO> productForecastDtoList = productForecastDAO.getForecastEntries(userId);
        if (productForecastDtoList.isEmpty()) {
            return responseBuilder.createFailureResponse(Response.Status.NOT_FOUND, Constants.SERVICE_USER_NOT_FOUND);
        } else {
            return responseBuilder.createSuccessResponse(productForecastDtoList);
        }
    }

    @Override
    public Response updateForecastEntries(List<ProductForecast> productForecastList, Integer userId) throws ServiceException {
        try {
            for (ProductForecast productForecast : productForecastList) {
                LOGGER.info("Validating entries for forecast id {}", productForecast.getProductForecastId());
                productForecastValidator.validate(productForecast);
            }
            LOGGER.info("Attempting to update forecast entries for user{}", userId);
            String returnMessage = productForecastDAO.updateForecastEntries(productForecastList, userId);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_UPDATED, ProductType.FORECAST);
            return responseBuilder.createSuccessResponse(returnMessage);
        } catch (Exception e) {
            LOGGER.error("Could not update forecast entry for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            return responseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_UPDATING_ENTRY);
        }
    }

    @Override
    public Response deleteForecastEntry(List<Integer> productForecastId, Integer userId) throws ServiceException {
        try {
            LOGGER.info("Attempting to delete forecast entry: " + productForecastId);
            String returnMessage = productForecastDAO.deleteForecastEntry(productForecastId);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_REMOVED, ProductType.FORECAST);
            return responseBuilder.createSuccessResponse(returnMessage);
        } catch (Exception e) {
            LOGGER.error("Could not delete product forecast entry {}", productForecastId);
            LOGGER.error(e.getMessage(), e);
            return responseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_DELETING_ENTRY);
        }
    }
}
