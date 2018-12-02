package com.itavery.forecast.service.forecast;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  1/30/18
 |
 *===========================================================================*/

import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;

import java.util.List;

public interface ProductForecastService {

    String addForecastEntry(ProductForecastDTO productForecast, Integer userId) throws ServiceException;

    List<ProductForecastDTO> getForecastEntries(Integer userId) throws ServiceException;

    String updateForecastEntries(List<ProductForecast> productForecastList, Integer userId) throws ServiceException;

    String deleteForecastEntry(List<Integer> productForecastId, Integer userId) throws ServiceException;
}
