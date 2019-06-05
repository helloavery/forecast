package com.itavery.forecast.constants;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-08
 * https://github.com/helloavery
 */

public enum ProductType {

    DEMAND("D", "Product Demand"),
    FORECAST("F", "Product Forecast");

    private String code;
    private String value;

    ProductType(String code, String value) {
        this.code = code;
        this.value = value;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
