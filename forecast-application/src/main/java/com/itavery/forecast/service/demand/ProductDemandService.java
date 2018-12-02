package com.itavery.forecast.service.demand;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  1/30/18
 |
 *===========================================================================*/

import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;

import java.util.List;

public interface ProductDemandService {

    String addDemandEntry(ProductDemandDTO productDemand, Integer userId) throws ServiceException;

    ProductDemandDTO getDemandEntry(Integer productDemandId) throws ServiceException;

    List<ProductDemandDTO> getUserDemandEntries(Integer userId) throws ServiceException;

    void updateDemandEntry(List<ProductDemand> productDemandList, Integer userId) throws ServiceException;

    String removeDemandEntry(List<Integer> productDemandId, Integer userId) throws ServiceException;
}
