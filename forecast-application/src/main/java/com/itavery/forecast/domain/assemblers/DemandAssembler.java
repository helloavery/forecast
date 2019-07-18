package com.itavery.forecast.domain.assemblers;

import com.itavery.forecast.domain.mithra.product.ProductDemandDB;
import com.itavery.forecast.product.ProductDemandDTO;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-09
 * https://github.com/helloavery
 */

@Component
public class DemandAssembler {

    public ProductDemandDTO covertToDTO(ProductDemandDB productDemandDB) {
        ProductDemandDTO productDemandDTO = new ProductDemandDTO();
        productDemandDTO.setEntryDate(productDemandDB.getEntryDate());
        productDemandDTO.setProductCode(productDemandDB.getProductCode());
        productDemandDTO.setWarehouse(productDemandDB.getWarehouse());
        productDemandDTO.setProductCategory(productDemandDB.getProductCategory());
        productDemandDTO.setOrderDemand(productDemandDB.getOrderDemand());
        return productDemandDTO;
    }

    public ProductDemandDB covertToDB(ProductDemandDTO productDemandDTO) {
        ProductDemandDB productDemandDB = new ProductDemandDB();
        productDemandDB.setEntryDate(new Timestamp(new Date().getTime()));
        productDemandDB.setProductCode(productDemandDTO.getProductCode());
        productDemandDB.setWarehouse(productDemandDTO.getWarehouse());
        productDemandDB.setProductCategory(productDemandDTO.getProductCategory());
        productDemandDB.setOrderDemand(productDemandDTO.getOrderDemand());
        return productDemandDB;
    }
}
