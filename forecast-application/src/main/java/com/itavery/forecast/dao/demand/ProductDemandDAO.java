package com.itavery.forecast.dao.demand;
/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  2/10/18
 |
 *===========================================================================*/

import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;

import java.util.List;

public interface ProductDemandDAO {

    String addDemandEntry(Integer userId, ProductDemandDTO productDemandDTO) throws DAOException;

    ProductDemandDTO getDemandEntry(Integer productDemandId) throws DAOException;

    List<ProductDemandDTO> getUserDemandEntries(Integer userId) throws DAOException;

    void updateDemandEntry(List<ProductDemand> productDemandList, Integer userId) throws DAOException;

    String removeDemandEntry(List<Integer> productDemandId) throws DAOException;
}
