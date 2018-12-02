package com.itavery.forecast.product;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  10/8/18            
 |            
 *===========================================================================*/

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
