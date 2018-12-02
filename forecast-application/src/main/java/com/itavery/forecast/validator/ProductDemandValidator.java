package com.itavery.forecast.validator;

import com.itavery.forecast.exceptions.InvalidInputException;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ProductDemandValidator {

    private static final Logger LOGGER = LogManager.getLogger(ProductDemandValidator.class);

    @SuppressWarnings("Duplicates")
    public void validate(ProductDemandDTO productDemandDTO) throws InvalidInputException {
        try {
            if (!isValidProductCode(productDemandDTO.getProductCode())) {
                throw new InvalidInputException("ProductDemandValidator_INVALID_PRODUCT_CODE");
            } else if (!isValidWarehouse(productDemandDTO.getWarehouse())) {
                throw new InvalidInputException("ProductDemandValidator_INVALID_WAREHOUSE");
            } else if (!isValidProductCategory(productDemandDTO.getProductCategory())) {
                throw new InvalidInputException("ProductDemandValidator_INVALID_PRODUCT_CATEGORY");
            } else if (!isValidOrderDemand(productDemandDTO.getOrderDemand())) {
                throw new InvalidInputException("ProductDemandValidator_INVALID_ORDER_DEMAND");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @SuppressWarnings("Duplicates")
    public void validate(ProductDemand productDemand) throws InvalidInputException {
        try {
            if (!isValidProductCode(productDemand.getProductCode())) {
                throw new InvalidInputException("ProductDemandValidator_INVALID_PRODUCT_CODE");
            } else if (!isValidWarehouse(productDemand.getWarehouse())) {
                throw new InvalidInputException("ProductDemandValidator_INVALID_WAREHOUSE");
            } else if (!isValidProductCategory(productDemand.getProductCategory())) {
                throw new InvalidInputException("ProductDemandValidator_INVALID_PRODUCT_CATEGORY");
            } else if (!isValidOrderDemand(productDemand.getOrderDemand())) {
                throw new InvalidInputException("ProductDemandValidator_INVALID_ORDER_DEMAND");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    private Boolean isValidProductCode(String productCode) {
        String regexPattern = "Product_[1000,9999]";
        return productCode.matches(regexPattern);
    }

    private Boolean isValidWarehouse(String warehouse) {
        String regexPattern = "Whse_[A-Z]{1}";
        return warehouse.matches(regexPattern);
    }

    private Boolean isValidProductCategory(String productCategory) {
        String regexPattern = "Category_[\\d]{3}";
        return productCategory.matches(regexPattern);
    }

    private Boolean isValidOrderDemand(String orderDemand) {
        String regexPatternOne = "[\\d]+";
        String regexPatternTwo = "([\\d]+)";
        if (!orderDemand.matches(regexPatternOne)) {
            return orderDemand.matches(regexPatternTwo);
        } else {
            return orderDemand.matches(regexPatternOne);
        }
    }
}
