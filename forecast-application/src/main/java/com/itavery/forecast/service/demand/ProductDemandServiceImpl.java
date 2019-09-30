package com.itavery.forecast.service.demand;

import com.itavery.forecast.Constants;
import com.itavery.forecast.dao.demand.ProductDemandDAO;
import com.itavery.forecast.functional.AuditType;
import com.itavery.forecast.functional.ProductType;
import com.itavery.forecast.request.ProductDemandRequest;
import com.itavery.forecast.response.ProductDemandResponse;
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
 * Created on: 2018-01-30
 * https://github.com/helloavery
 */

@Service
public class ProductDemandServiceImpl implements ProductDemandService {

    private final static Logger LOGGER = LogManager.getLogger(ProductDemandServiceImpl.class);
    private AuditService auditService;
    private ProductDemandDAO productDemandDAO;

    @Inject
    public ProductDemandServiceImpl(ProductDemandDAO productDemandDAO){
        this.productDemandDAO = productDemandDAO;
    }

    @Inject
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public Response addDemandEntry(ProductDemandRequest pdRequest, int userId) throws ServiceException {
        try {
            String result = productDemandDAO.addDemandEntry(userId, pdRequest);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_ADDED, ProductType.DEMAND);
            LOGGER.info("Attempting to add product entry for user {}", userId);
            return ResponseBuilder.createSuccessResponse(result);
        } catch (Exception e) {
            LOGGER.error("Could not add demand entry for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_ADDING_ENTRY);
        }
    }

    @Override
    public Response getDemandEntry(int productDemandId) throws ServiceException {
        LOGGER.info("Attempting to get product entry: " + productDemandId);
        ProductDemandResponse response = productDemandDAO.getDemandEntry(productDemandId);
        if (response == null) {
            LOGGER.error("Could not get demand entry for user: " + productDemandId);
            return ResponseBuilder.createFailureResponse(Response.Status.NOT_FOUND, Constants.SERVICE_ENTRY_NOT_FOUND);
        }
        return ResponseBuilder.createSuccessResponse(response);
    }

    @Override
    public Response getUserDemandEntries(int userId) throws ServiceException {
        LOGGER.info("Attempting to get product entries for user: " + userId);
        List<ProductDemandResponse> productDemandList = productDemandDAO.getUserDemandEntries(userId);
        if (CollectionUtils.isEmpty(productDemandList)) {
            LOGGER.error("Could not get demand entries for user: " + userId);
            return ResponseBuilder.createFailureResponse(Response.Status.NOT_FOUND, Constants.SERVICE_USER_NOT_FOUND);
        }
        return ResponseBuilder.createSuccessResponse(productDemandList);
    }

    @Override
    public Response updateDemandEntry(List<ProductDemandRequest> pdRequest, int userId) throws ServiceException {
        try {
            LOGGER.info("Attempting to update demand entries for user {}", userId);
            productDemandDAO.updateDemandEntry(pdRequest, userId);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_UPDATED, ProductType.DEMAND);
            return ResponseBuilder.createSuccessResponse();
        } catch (Exception e) {
            LOGGER.error("Could not update demand entries {}", pdRequest);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_UPDATING_ENTRY);
        }
    }

    @Override
    public Response removeDemandEntry(List<Integer> productDemandId, int userId) throws ServiceException {
        try {
            LOGGER.info("Attempting to delete demand entry for: " + productDemandId);
            String returnMessage = productDemandDAO.removeDemandEntry(productDemandId);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_REMOVED, ProductType.DEMAND);
            return ResponseBuilder.createSuccessResponse(returnMessage);
        } catch (Exception e) {
            LOGGER.error("Could not delete demand entries {}", productDemandId);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_UPDATING_ENTRY);
        }
    }
}
