package com.itavery.forecast.dao.forecast;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  2/10/18
 |
 *===========================================================================*/

import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;

import java.util.List;

public interface ProductForecastDAO {

    String addForecastEntry(Integer userId, ProductForecastDTO productForecast) throws DAOException;

    List<ProductForecastDTO> getForecastEntries(Integer userId) throws DAOException;

    String updateForecastEntries(List<ProductForecast> productForecastList, Integer userId) throws DAOException;

    String deleteForecastEntry(List<Integer> productForecastId) throws DAOException;
}
