package com.itavery.forecast.service.demand;

import com.itavery.forecast.Constants;
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

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-01-30
 * https://github.com/helloavery
 */

@Service
public class ProductDemandServiceImpl implements ProductDemandService {

    private final static Logger LOGGER = LogManager.getLogger(ProductDemandServiceImpl.class);
    @Inject
    private AuditService auditService;
    @Inject
    private ProductDemandDAO productDemandDAO;
    @Inject
    private ProductDemandValidator productDemandValidator;

    @Override
    public String addDemandEntry(ProductDemandDTO productDemand, Integer userId) throws ServiceException {
        String response = null;
        try {
            LOGGER.info("Validating product demand entry/entries for user {}", userId);
            productDemandValidator.validate(productDemand);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_ADDED, ProductType.DEMAND);
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
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_UPDATED, ProductType.DEMAND);
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
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ENTRY_REMOVED, ProductType.DEMAND);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not delete demand entries {}", productDemandId);
                LOGGER.error(e.getMessage(), e);
            }
        }
        return returnMessage;
    }
}
