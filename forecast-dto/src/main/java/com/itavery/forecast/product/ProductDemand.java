package com.itavery.forecast.product;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  2/10/18
 |
 *===========================================================================*/


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class ProductDemand implements Serializable {

    private Integer productDemandId;
    private String productCode;
    private String warehouse;
    private Timestamp entryDate;
    private String productCategory;
    private String orderDemand;
    private Integer userId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDemand productDemand = (ProductDemand) o;
        return Objects.equals(productDemandId, productDemand.getProductDemandId()) &&
                Objects.equals(productCode, productDemand.getProductCode()) &&
                Objects.equals(entryDate, productDemand.getEntryDate()) &&
                Objects.equals(warehouse, productDemand.getWarehouse()) &&
                Objects.equals(productCategory, productDemand.getProductCategory()) &&
                Objects.equals(orderDemand, productDemand.getOrderDemand()) &&
                Objects.equals(userId, productDemand.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productDemandId, productCode, entryDate, warehouse, productCategory, orderDemand, userId);
    }
}

