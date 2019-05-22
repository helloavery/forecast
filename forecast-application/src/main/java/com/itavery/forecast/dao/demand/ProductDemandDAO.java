package com.itavery.forecast.dao.demand;

import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;

import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-10
 * https://github.com/helloavery
 */

public interface ProductDemandDAO {

    String addDemandEntry(Integer userId, ProductDemandDTO productDemandDTO) throws DAOException;

    ProductDemandDTO getDemandEntry(Integer productDemandId) throws DAOException;

    List<ProductDemandDTO> getUserDemandEntries(Integer userId) throws DAOException;

    void updateDemandEntry(List<ProductDemand> productDemandList, Integer userId) throws DAOException;

    String removeDemandEntry(List<Integer> productDemandId) throws DAOException;
}
