package com.itavery.forecast.utils.validation;

import com.itavery.forecast.Constants;
import com.itavery.forecast.request.ProductForecastRequest;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ProductForecastValidator implements ConstraintValidator<ValidProductForecastRequest, ProductForecastRequest> {

    private static final Logger LOGGER = LogManager.getLogger(ProductForecastValidator.class);

    @Override
    public void initialize(ValidProductForecastRequest constraintAnnotation) {
    }

    @Override
    public boolean isValid(ProductForecastRequest productForecastRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (!isValidSku(productForecastRequest.getSku())) {
            LOGGER.warn(Constants.FORECAST_INVALID_SKU);
            return false;
        }
        else if (!isValidNationalInventory(productForecastRequest.getNationalInventory())) {
            LOGGER.warn(Constants.FORECAST_INVALID_NATIONAL_INVENTORY);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getLeadTime())) {
            LOGGER.warn(Constants.FORECAST_INVALID_LEAD_TIME);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getInTransitQty())) {
            LOGGER.warn(Constants.FORECAST_INVALID_IN_TRANSIT_QTY);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getForecastThreeMonths())) {
            LOGGER.warn(Constants.FORECAST_INVALID_FORECAST_THREE_MONTHS);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getForecastSixMonths())) {
            LOGGER.warn(Constants.FORECAST_INVALID_FORECAST_SIX_MONTHS);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getForecastNineMonths())) {
            LOGGER.warn(Constants.FORECAST_INVALID_FORECAST_NINE_MONTHS);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getSalesOneMonth())) {
            LOGGER.warn(Constants.FORECAST_INVALID_SALES_ONE_MONTH);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getSalesThreeMonths())) {
            LOGGER.warn(Constants.FORECAST_INVALID_SALES_THREE_MONTH);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getSalesSixMonths())) {
            LOGGER.warn(Constants.FORECAST_INVALID_SALES_SIX_MONTH);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getSalesNineMonths())) {
            LOGGER.warn(Constants.FORECAST_INVALID_SALES_NINE_MONTH);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getMinBank())) {
            LOGGER.warn(Constants.FORECAST_INVALID_MIN_BANK);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getPiecesPastDue())) {
            LOGGER.warn(Constants.FORECAST_INVALID_PIECES_PAST_DUE);
            return false;
        }
        else if (!isValidInteger(productForecastRequest.getLocalBoQty())) {
            LOGGER.warn(Constants.FORECAST_INVALID_LOCAL_BO_QTY);
            return false;
        }
        else if (!isValidOption(productForecastRequest.getPotentialIssues())) {
            LOGGER.warn(Constants.FORECAST_INVALID_POTENTIAL_ISSUES);
            return false;
        }
        else if (!isValidOption(productForecastRequest.getDeckRisk())) {
            LOGGER.warn(Constants.FORECAST_INVALID_DECK_RISK);
            return false;
        }
        else if (!isValidOption(productForecastRequest.getPpapRisk())) {
            LOGGER.warn(Constants.FORECAST_INVALID_PPAP_RISK);
            return false;
        }
        else if (!isValidOption(productForecastRequest.getStopAutoBuy())) {
            LOGGER.warn(Constants.FORECAST_INVALID_STOP_AUTO_BUY);
            return false;
        }
        else if (!isValidOption(productForecastRequest.getRevStop())) {
            LOGGER.warn(Constants.FORECAST_INVALID_REV_STOP);
            return false;
        }
        else if (!isValidOption(productForecastRequest.getBackorder())) {
            LOGGER.warn(Constants.FORECAST_INVALID_BACKORDER);
            return false;
        }
        else if (!isValidDouble(productForecastRequest.getPerfSixMonthAvg())) {
            LOGGER.warn(Constants.FORECAST_INVALID_PERF_SIX_MONTH_AVG);
            return false;
        }
        else if (!isValidDouble(productForecastRequest.getPerfTwelveMonthAvg())) {
            LOGGER.warn(Constants.FORECAST_INVALID_PERF_TWELVE_MONTH_AVG);
            return false;
        }
        return true;
    }

    /**
     * sku validation
     *
     * @param sku
     * @return
     */
    private boolean isValidSku(int sku) {
        return (sku >= 1000000 && sku <= 9999999);
    }

    /**
     * National inventory validation; only integer that CAN be negative
     *
     * @param nationalInventory
     * @return
     */
    private boolean isValidNationalInventory(Integer nationalInventory) {
        return nationalInventory != null;
    }

    /**
     * validation for:
     * leadTime, inTransitQty, forecastThreeMonths, forecastSixMonths, forecastNineMonths,
     * salesOneMonth, salesThreeMonths, salesSixMonths, salesNineMonths,
     * minBank, piecesPastDue, localBoQty
     * <p>
     * These integers CAN be null but CANNOT be negative
     *
     * @param value to be validated
     * @return
     */
    private boolean isValidInteger(Object value) {
        return value == null || (NumberUtils.isDigits(value.toString()) && NumberUtils.toInt(value.toString()) > 0);
    }

    /**
     * validation for all the Char values:
     * potentialIssues, deckRisk, ppapRisk, stopAutoBuy, revStop, backorder
     *
     * @param value to be validated
     * @return
     */
    private boolean isValidOption(Object value) {
        return BooleanUtils.toBooleanObject(value.toString()) != null;
    }

    /**
     * validation for double values:
     * perfSixMonthAvg, perfTwelveMonthAvg;
     * These doubles CAN be null
     *
     * @param value to be validated
     * @return boolean
     */
    private boolean isValidDouble(Object value) {
        return value instanceof Double;
    }
}
