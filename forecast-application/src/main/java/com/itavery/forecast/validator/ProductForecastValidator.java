package com.itavery.forecast.validator;

import com.itavery.forecast.exceptions.InvalidInputException;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductForecastValidator {

    @SuppressWarnings("Duplicates")
    public void validate(ProductForecast productForecast) throws InvalidInputException {
        if (!isValidSku(productForecast.getSku())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SKU");
        }
        if (!isValidNationalInventory(productForecast.getNationalInventory())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_NATIONAL_INVENTORY");
        }
        if (!isValidInteger(productForecast.getLeadTime())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_LEAD_TIME");
        }
        if (!isValidInteger(productForecast.getInTransitQty())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_IN_TRANSIT_QTY");
        }
        if (!isValidInteger(productForecast.getForecastThreeMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_FORECAST_THREE_MONTHS");
        }
        if (!isValidInteger(productForecast.getForecastSixMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_FORECAST_SIX_MONTHS");
        }
        if (!isValidInteger(productForecast.getForecastNineMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_FORECAST_NINE_MONTHS");
        }
        if (!isValidInteger(productForecast.getSalesOneMonth())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SALES_ONE_MONTH");
        }
        if (!isValidInteger(productForecast.getSalesThreeMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SALES_THREE_MONTH");
        }
        if (!isValidInteger(productForecast.getSalesSixMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SALES_SIX_MONTH");
        }
        if (!isValidInteger(productForecast.getSalesNineMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SALES_NINE_MONTH");
        }
        if (!isValidInteger(productForecast.getMinBank())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_MIN_BANK");
        }
        if (!isValidInteger(productForecast.getPiecesPastDue())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_PIECES_PAST_DUE");
        }
        if (!isValidInteger(productForecast.getLocalBoQty())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_LOCAL_BO_QTY");
        }
        if (!isValidOption(productForecast.getPotentialIssues())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_POTENTIAL_ISSUES");
        }
        if (!isValidOption(productForecast.getDeckRisk())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_DECK_RISK");
        }
        if (!isValidOption(productForecast.getPpapRisk())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_PPAP_RISK");
        }
        if (!isValidOption(productForecast.getStopAutoBuy())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_STOP_AUTO_BUY");
        }
        if (!isValidOption(productForecast.getRevStop())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_REV_STOP");
        }
        if (!isValidOption(productForecast.getBackorder())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_BACKORDER");
        }
        if (!isValidDouble(productForecast.getPerfSixMonthAvg())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_PERF_SIX_MONTH_AVG");
        }
        if (!isValidDouble(productForecast.getPerfTwelveMonthAvg())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_PERF_TWELVE_MONTH_AVG");
        }
    }

    @SuppressWarnings("Duplicates")
    public void validate(ProductForecastDTO productForecastDTO) throws InvalidInputException {
        if (!isValidSku(productForecastDTO.getSku())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SKU");
        }
        if (!isValidNationalInventory(productForecastDTO.getNationalInventory())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_NATIONAL_INVENTORY");
        }
        if (!isValidInteger(productForecastDTO.getLeadTime())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_LEAD_TIME");
        }
        if (!isValidInteger(productForecastDTO.getInTransitQty())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_IN_TRANSIT_QTY");
        }
        if (!isValidInteger(productForecastDTO.getForecastThreeMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_FORECAST_THREE_MONTHS");
        }
        if (!isValidInteger(productForecastDTO.getForecastSixMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_FORECAST_SIX_MONTHS");
        }
        if (!isValidInteger(productForecastDTO.getForecastNineMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_FORECAST_NINE_MONTHS");
        }
        if (!isValidInteger(productForecastDTO.getSalesOneMonth())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SALES_ONE_MONTH");
        }
        if (!isValidInteger(productForecastDTO.getSalesThreeMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SALES_THREE_MONTH");
        }
        if (!isValidInteger(productForecastDTO.getSalesSixMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SALES_SIX_MONTH");
        }
        if (!isValidInteger(productForecastDTO.getSalesNineMonths())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_SALES_NINE_MONTH");
        }
        if (!isValidInteger(productForecastDTO.getMinBank())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_MIN_BANK");
        }
        if (!isValidInteger(productForecastDTO.getPiecesPastDue())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_PIECES_PAST_DUE");
        }
        if (!isValidInteger(productForecastDTO.getLocalBoQty())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_LOCAL_BO_QTY");
        }
        if (!isValidOption(productForecastDTO.getPotentialIssues())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_POTENTIAL_ISSUES");
        }
        if (!isValidOption(productForecastDTO.getDeckRisk())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_DECK_RISK");
        }
        if (!isValidOption(productForecastDTO.getPpapRisk())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_PPAP_RISK");
        }
        if (!isValidOption(productForecastDTO.getStopAutoBuy())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_STOP_AUTO_BUY");
        }
        if (!isValidOption(productForecastDTO.getRevStop())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_REV_STOP");
        }
        if (!isValidOption(productForecastDTO.getBackorder())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_BACKORDER");
        }
        if (!isValidDouble(productForecastDTO.getPerfSixMonthAvg())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_PERF_SIX_MONTH_AVG");
        }
        if (!isValidDouble(productForecastDTO.getPerfTwelveMonthAvg())) {
            throw new InvalidInputException("ProductForecastValidator_INVALID_PERF_TWELVE_MONTH_AVG");
        }
    }

    /**
     * sku validation
     *
     * @param sku
     * @return
     */
    private Boolean isValidSku(int sku) {
        return (sku >= 1000000 && sku <= 9999999);
    }

    /**
     * National inventory validation; only integer that CAN be negative
     *
     * @param nationalInventory
     * @return
     */
    private Boolean isValidNationalInventory(Integer nationalInventory) {
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
     * @param value
     * @return
     */
    private Boolean isValidInteger(Integer value) {
        return value == null || value >= 0;
    }

    /**
     * validation for all the Char values:
     * potentialIssues, deckRisk, ppapRisk, stopAutoBuy, revStop, backorder
     *
     * @param value
     * @return
     */
    private Boolean isValidOption(String value) {
        return value.equals("true") || value.equals("false");
    }

    /**
     * validation for double values:
     * perfSixMonthAvg, perfTwelveMonthAvg;
     * These doubles CAN be null
     *
     * @param values x number of Doubles
     * @return boolean
     */
    private Boolean isValidDouble(Double... values) {
        Boolean validation = null;
        for (Double value : values) {
            if (value != null) {
                if (value == (double) value) {
                    validation = true;
                } else {
                    validation = false;
                }
            } else {
                validation = true;
            }
        }
        return validation;
    }
}
