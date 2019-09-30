package com.itavery.forecast.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/27/19
 * https://github.com/helloavery
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDemandRequest {

    private Integer productDemandId;
    private String productCode;
    private String warehouse;
    private Timestamp entryDate;
    private String productCategory;
    private String orderDemand;
    private int userId;

    public Integer getProductDemandId() {
        return productDemandId;
    }

    public void setProductDemandId(Integer productDemandId) {
        this.productDemandId = productDemandId;
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

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
