package com.itavery.forecast.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/27/19
 * https://github.com/helloavery
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDemandResponse {

    private Date entryDate;
    private String productCode;
    private String warehouse;
    private String productCategory;
    private String orderDemand;

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getOrderDemand() {
        return orderDemand;
    }

    public void setOrderDemand(String orderDemand) {
        this.orderDemand = orderDemand;
    }
}
