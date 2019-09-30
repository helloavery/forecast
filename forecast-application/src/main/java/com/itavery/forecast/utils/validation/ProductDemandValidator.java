package com.itavery.forecast.utils.validation;

import com.itavery.forecast.Constants;
import com.itavery.forecast.request.ProductDemandRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ProductDemandValidator implements ConstraintValidator<ValidProductDemandRequest, ProductDemandRequest> {

    private static final Logger LOGGER = LogManager.getLogger(ProductDemandValidator.class);

    @Override
    public void initialize(ValidProductDemandRequest constraintAnnotation) {
    }

    @Override
    public boolean isValid(ProductDemandRequest productDemandRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (!isValidProductCode(productDemandRequest.getProductCode())) {
            LOGGER.warn(Constants.DEMAND_VALIDATOR_INVALID_PRODUCT_CODE);
            return false;
        } else if (!isValidWarehouse(productDemandRequest.getWarehouse())) {
            LOGGER.warn(Constants.DEMAND_VALIDATOR_INVALID_WAREHOUSE);
            return false;
        } else if (!isValidProductCategory(productDemandRequest.getProductCategory())) {
            LOGGER.warn(Constants.DEMAND_VALIDATOR_INVALID_PRODUCT_CATEGORY);
            return false;
        } else if (!isValidOrderDemand(productDemandRequest.getOrderDemand())) {
            LOGGER.warn(Constants.DEMAND_VALIDATOR_INVALID_ORDER_DEMAND);
            return false;
        }
        return true;
    }

    private boolean isValidProductCode(String productCode) {
        String regexPattern = "Product_[1000,9999]";
        return productCode.matches(regexPattern);
    }

    private boolean isValidWarehouse(String warehouse) {
        String regexPattern = "Whse_[A-Z]{1}";
        return warehouse.matches(regexPattern);
    }

    private boolean isValidProductCategory(String productCategory) {
        String regexPattern = "Category_[\\d]{3}";
        return productCategory.matches(regexPattern);
    }

    private boolean isValidOrderDemand(String orderDemand) {
        String regexPatternOne = "[\\d]+";
        String regexPatternTwo = "([\\d]+)";
        if (!orderDemand.matches(regexPatternOne)) {
            return orderDemand.matches(regexPatternTwo);
        } else {
            return orderDemand.matches(regexPatternOne);
        }
    }
}
