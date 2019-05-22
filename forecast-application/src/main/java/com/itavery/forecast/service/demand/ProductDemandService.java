package com.itavery.forecast.service.demand;

import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;

import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-01-30
 * https://github.com/helloavery
 */

public interface ProductDemandService {

    String addDemandEntry(ProductDemandDTO productDemand, Integer userId) throws ServiceException;

    ProductDemandDTO getDemandEntry(Integer productDemandId) throws ServiceException;

    List<ProductDemandDTO> getUserDemandEntries(Integer userId) throws ServiceException;

    void updateDemandEntry(List<ProductDemand> productDemandList, Integer userId) throws ServiceException;

    String removeDemandEntry(List<Integer> productDemandId, Integer userId) throws ServiceException;
}
