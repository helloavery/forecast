package com.itavery.forecast.service.forecast;

import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-30
 * https://github.com/helloavery
 */

public interface ProductForecastService {

    Response addForecastEntry(ProductForecastDTO productForecast, Integer userId) throws ServiceException;

    Response getForecastEntries(Integer userId) throws ServiceException;

    Response updateForecastEntries(List<ProductForecast> productForecastList, Integer userId) throws ServiceException;

    Response deleteForecastEntry(List<Integer> productForecastId, Integer userId) throws ServiceException;
}
