package com.itavery.forecast.service.forecast;

import com.itavery.forecast.Constants;
import com.itavery.forecast.dao.forecast.ProductForecastDAO;
import com.itavery.forecast.functional.AuditType;
import com.itavery.forecast.functional.ProductType;
import com.itavery.forecast.request.ProductForecastRequest;
import com.itavery.forecast.response.ProductForecastResponse;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.utils.ResponseBuilder;
import com.itavery.forecast.utils.exceptions.ServiceException;
import org.apache.commons.collections.CollectionUtils;
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
    private AuditService auditService;
    private ProductForecastDAO productForecastDAO;

    @Inject
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Inject
    public void setProductForecastDAO(ProductForecastDAO productForecastDAO) {
        this.productForecastDAO = productForecastDAO;
    }

    @Override
    public Response addForecastEntry(ProductForecastRequest pfRequest, Integer userId) throws ServiceException {
        try {
            String returnMessage = productForecastDAO.addForecastEntry(userId, pfRequest);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_ADDED, ProductType.FORECAST);
            LOGGER.info("Attempting to add forecast entry for user {}", userId);
            return ResponseBuilder.createSuccessResponse(returnMessage);
        } catch (Exception e) {
            LOGGER.error("Could not add forecast entry for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_ADDING_ENTRY);
        }
    }

    @Override
    public Response getForecastEntries(Integer userId) throws ServiceException {
        LOGGER.info("Attempting to get forecast entries for user {}", userId);
        List<ProductForecastResponse> productForecastDtoList = productForecastDAO.getForecastEntries(userId);
        if (CollectionUtils.isEmpty(productForecastDtoList)) {
            return ResponseBuilder.createFailureResponse(Response.Status.NOT_FOUND, Constants.SERVICE_USER_NOT_FOUND);
        } else {
            return ResponseBuilder.createSuccessResponse(productForecastDtoList);
        }
    }

    @Override
    public Response updateForecastEntries(List<ProductForecastRequest> productForecastList, Integer userId) throws ServiceException {
        try {
            LOGGER.info("Attempting to update forecast entries for user{}", userId);
            String returnMessage = productForecastDAO.updateForecastEntries(productForecastList, userId);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_UPDATED, ProductType.FORECAST);
            return ResponseBuilder.createSuccessResponse(returnMessage);
        } catch (Exception e) {
            LOGGER.error("Could not update forecast entry for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_UPDATING_ENTRY);
        }
    }

    @Override
    public Response deleteForecastEntry(List<Integer> productForecastId, Integer userId) throws ServiceException {
        try {
            LOGGER.info("Attempting to delete forecast entry: " + productForecastId);
            String returnMessage = productForecastDAO.deleteForecastEntry(productForecastId);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_REMOVED, ProductType.FORECAST);
            return ResponseBuilder.createSuccessResponse(returnMessage);
        } catch (Exception e) {
            LOGGER.error("Could not delete product forecast entry {}", productForecastId);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_DELETING_ENTRY);
        }
    }
}
