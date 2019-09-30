package com.itavery.forecast.dao.demand;

import com.itavery.forecast.request.ProductDemandRequest;
import com.itavery.forecast.response.ProductDemandResponse;
import com.itavery.forecast.utils.exceptions.DAOException;

import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-10
 * https://github.com/helloavery
 */

public interface ProductDemandDAO {

    String addDemandEntry(int userId, ProductDemandRequest pdRequest) throws DAOException;

    ProductDemandResponse getDemandEntry(int productDemandId) throws DAOException;

    List<ProductDemandResponse> getUserDemandEntries(int userId) throws DAOException;

    void updateDemandEntry(List<ProductDemandRequest> pdRequest, int userId) throws DAOException;

    String removeDemandEntry(List<Integer> productDemandId) throws DAOException;
}
