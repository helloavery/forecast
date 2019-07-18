package com.itavery.forecast.service.demand;

import com.itavery.forecast.ResponseBuilder;
import com.itavery.forecast.constants.AuditType;
import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.constants.ProductType;
import com.itavery.forecast.dao.demand.ProductDemandDAO;
import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.validator.ProductDemandValidator;
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
    private ProductDemandValidator productDemandValidator;

    @Inject
    public ProductDemandServiceImpl(ProductDemandDAO productDemandDAO){
        this.productDemandDAO = productDemandDAO;
    }

    @Inject
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Inject
    public void setProductDemandValidator(ProductDemandValidator productDemandValidator) {
        this.productDemandValidator = productDemandValidator;
    }

    @Override
    public Response addDemandEntry(ProductDemandDTO productDemand, Integer userId) throws ServiceException {
        try {
            LOGGER.info("Validating product demand entry/entries for user {}", userId);
            productDemandValidator.validate(productDemand);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_ADDED, ProductType.DEMAND);
            LOGGER.info("Attempting to add product entry for user {}", userId);
            String result = productDemandDAO.addDemandEntry(userId, productDemand);
            return ResponseBuilder.createSuccessResponse(result);
        } catch (Exception e) {
            LOGGER.error("Could not add demand entry for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_ADDING_ENTRY);
        }
    }

    @Override
    public Response getDemandEntry(Integer productDemandId) throws ServiceException {
        LOGGER.info("Attempting to get product entry: " + productDemandId);
        ProductDemandDTO productDemandDto = productDemandDAO.getDemandEntry(productDemandId);
        if (productDemandDto == null) {
            LOGGER.error("Could not get demand entry for user: " + productDemandId);
            return ResponseBuilder.createFailureResponse(Response.Status.NOT_FOUND, Constants.SERVICE_ENTRY_NOT_FOUND);
        }
        return ResponseBuilder.createSuccessResponse(productDemandDto);
    }

    @Override
    public Response getUserDemandEntries(Integer userId) throws ServiceException {
        LOGGER.info("Attempting to get product entries for user: " + userId);
        List<ProductDemandDTO> productDemandDtoList = productDemandDAO.getUserDemandEntries(userId);
        if (productDemandDtoList.isEmpty()) {
            LOGGER.error("Could not get demand entries for user: " + userId);
            return ResponseBuilder.createFailureResponse(Response.Status.NOT_FOUND, Constants.SERVICE_USER_NOT_FOUND);
        }
        return ResponseBuilder.createSuccessResponse(productDemandDtoList);
    }

    @Override
    public Response updateDemandEntry(List<ProductDemand> productDemandList, Integer userId) throws ServiceException {
        try {
            for (ProductDemand productDemand : productDemandList) {
                productDemandValidator.validate(productDemand);
            }
            LOGGER.info("Attempting to update demand entries for user {}", userId);
            productDemandDAO.updateDemandEntry(productDemandList, userId);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_UPDATED, ProductType.DEMAND);
            return ResponseBuilder.createSuccessResponse();
        } catch (Exception e) {
            LOGGER.error("Could not update demand entries {}", productDemandList);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_UPDATING_ENTRY);
        }
    }

    @Override
    public Response removeDemandEntry(List<Integer> productDemandId, Integer userId) throws ServiceException {
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
