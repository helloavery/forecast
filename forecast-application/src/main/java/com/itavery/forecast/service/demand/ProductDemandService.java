package com.itavery.forecast.service.demand;

import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-01-30
 * https://github.com/helloavery
 */

public interface ProductDemandService {

    Response addDemandEntry(ProductDemandDTO productDemand, Integer userId) throws ServiceException;

    Response getDemandEntry(Integer productDemandId) throws ServiceException;

    Response getUserDemandEntries(Integer userId) throws ServiceException;

    Response updateDemandEntry(List<ProductDemand> productDemandList, Integer userId) throws ServiceException;

    Response removeDemandEntry(List<Integer> productDemandId, Integer userId) throws ServiceException;
}
