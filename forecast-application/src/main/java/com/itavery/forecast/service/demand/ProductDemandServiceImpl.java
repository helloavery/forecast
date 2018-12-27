package com.itavery.forecast.service.demand;

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
import com.itavery.forecast.dao.demand.ProductDemandDAO;
import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;
import com.itavery.forecast.product.ProductType;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.validator.ProductDemandValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ProductDemandServiceImpl implements ProductDemandService {

    private final static Logger LOGGER = LogManager.getLogger(ProductDemandServiceImpl.class);
    private final AuditService auditService;
    private final ProductDemandDAO productDemandDAO;
    private ProductDemandValidator productDemandValidator;

    @Inject
    public ProductDemandServiceImpl(final AuditService auditService, final ProductDemandDAO productDemandDAO, ProductDemandValidator productDemandValidator) {
        this.auditService = auditService;
        this.productDemandDAO = productDemandDAO;
        this.productDemandValidator = productDemandValidator;
    }

    @Override
    public String addDemandEntry(ProductDemandDTO productDemand, Integer userId) throws ServiceException {
        String response = null;
        try {
            LOGGER.info("Validating product demand entry/entries for user {}", userId);
            productDemandValidator.validate(productDemand);
            //TODO: Implement Audit Service
            auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ENTRY_ADDED, ProductType.DEMAND);
            LOGGER.info("Attempting to add product entry for user {}", userId);
            response = productDemandDAO.addDemandEntry(userId, productDemand);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not add demand entry for user {}", userId);
                LOGGER.error(e.getMessage(), e);
            }
        }
        return response;
    }

    @Override
    public ProductDemandDTO getDemandEntry(Integer productDemandId) throws ServiceException {
        LOGGER.info("Attempting to get product entry: " + productDemandId);
        ProductDemandDTO productDemandDto = productDemandDAO.getDemandEntry(productDemandId);
        if (productDemandDto == null) {
            LOGGER.error("Could not get demand entry for user: " + productDemandId);
            throw new ServiceException("DEMAND_ENTRIES_SERVICE.DEMAND_ENTRY_NOT_FOUND");
        }
        return productDemandDto;
    }

    @Override
    public List<ProductDemandDTO> getUserDemandEntries(Integer userId) throws ServiceException {
        LOGGER.info("Attempting to get product entries for user: " + userId);
        List<ProductDemandDTO> productDemandDtoList = productDemandDAO.getUserDemandEntries(userId);
        if (productDemandDtoList.isEmpty()) {
            LOGGER.error("Could not get demand entries for user: " + userId);
            throw new ServiceException("DEMAND_ENTRIES_SERVICE.USER_NOT_FOUND");
        }
        return productDemandDtoList;
    }

    @Override
    public void updateDemandEntry(List<ProductDemand> productDemandList, Integer userId) throws ServiceException {
        try {
            for (ProductDemand productDemand : productDemandList) {
                productDemandValidator.validate(productDemand);
            }
            LOGGER.info("Attempting to update demand entries for user {}", userId);
            productDemandDAO.updateDemandEntry(productDemandList, userId);
            //TODO: Implement Audit Service
            auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ENTRY_UPDATED, ProductType.DEMAND);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not update demand entries {}", productDemandList);
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String removeDemandEntry(List<Integer> productDemandId, Integer userId) throws ServiceException {
        String returnMessage = null;
        try {
            LOGGER.info("Attempting to delete demand entry for: " + productDemandId);
            returnMessage = productDemandDAO.removeDemandEntry(productDemandId);
            //TODO: Implement Audit Service
            auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ENTRY_REMOVED, ProductType.DEMAND);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not delete demand entries {}", productDemandId);
                LOGGER.error(e.getMessage(), e);
            }
        }
        return returnMessage;
    }
}
