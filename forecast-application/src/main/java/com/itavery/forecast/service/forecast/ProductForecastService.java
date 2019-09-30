package com.itavery.forecast.service.forecast;

import com.itavery.forecast.request.ProductForecastRequest;
import com.itavery.forecast.utils.exceptions.ServiceException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-30
 * https://github.com/helloavery
 */

public interface ProductForecastService {

    Response addForecastEntry(ProductForecastRequest pfRequest, Integer userId) throws ServiceException;

    Response getForecastEntries(Integer userId) throws ServiceException;

    Response updateForecastEntries(List<ProductForecastRequest> productForecastList, Integer userId) throws ServiceException;

    Response deleteForecastEntry(List<Integer> productForecastId, Integer userId) throws ServiceException;
}
