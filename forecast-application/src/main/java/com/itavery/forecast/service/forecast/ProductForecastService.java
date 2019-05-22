package com.itavery.forecast.service.forecast;

import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;

import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-30
 * https://github.com/helloavery
 */

public interface ProductForecastService {

    String addForecastEntry(ProductForecastDTO productForecast, Integer userId) throws ServiceException;

    List<ProductForecastDTO> getForecastEntries(Integer userId) throws ServiceException;

    String updateForecastEntries(List<ProductForecast> productForecastList, Integer userId) throws ServiceException;

    String deleteForecastEntry(List<Integer> productForecastId, Integer userId) throws ServiceException;
}
