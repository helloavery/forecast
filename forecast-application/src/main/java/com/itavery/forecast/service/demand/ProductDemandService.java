package com.itavery.forecast.service.demand;

import com.itavery.forecast.request.ProductDemandRequest;
import com.itavery.forecast.utils.exceptions.ServiceException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-01-30
 * https://github.com/helloavery
 */

public interface ProductDemandService {

    Response addDemandEntry(ProductDemandRequest pdRequest, int userId) throws ServiceException;

    Response getDemandEntry(int productDemandId) throws ServiceException;

    Response getUserDemandEntries(int userId) throws ServiceException;

    Response updateDemandEntry(List<ProductDemandRequest> pdRequest, int userId) throws ServiceException;

    Response removeDemandEntry(List<Integer> productDemandId, int userId) throws ServiceException;
}
