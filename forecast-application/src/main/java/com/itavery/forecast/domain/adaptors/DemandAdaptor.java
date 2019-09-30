package com.itavery.forecast.domain.adaptors;

import com.itavery.forecast.request.ProductDemandRequest;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-09
 * https://github.com/helloavery
 */

public class DemandAdaptor {

    public static DBObject toNewDBObject(ProductDemandRequest productDemand){
        return new BasicDBObject().append("productCode",productDemand.getProductCode())
                .append("warehouse", productDemand.getWarehouse()).append("entryDate",  productDemand.getEntryDate())
                .append("productCategory", productDemand.getProductCategory()).append("orderDemand", productDemand.getOrderDemand())
                .append("userId", productDemand.getUserId());
    }
    public static DBObject toDBObject(ProductDemandRequest productDemand){
        return new BasicDBObject("_id", productDemand.getProductDemandId()).append("productCode",productDemand.getProductCode())
                .append("warehouse", productDemand.getWarehouse()).append("entryDate",  productDemand.getEntryDate())
                .append("productCategory", productDemand.getProductCategory()).append("orderDemand", productDemand.getOrderDemand())
                .append("userId", productDemand.getUserId());
    }

}
