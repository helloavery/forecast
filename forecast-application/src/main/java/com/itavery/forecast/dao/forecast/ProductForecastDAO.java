package com.itavery.forecast.dao.forecast;

import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;

import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-10
 * https://github.com/helloavery
 */

public interface ProductForecastDAO {

    String addForecastEntry(Integer userId, ProductForecastDTO productForecast) throws DAOException;

    List<ProductForecastDTO> getForecastEntries(Integer userId) throws DAOException;

    String updateForecastEntries(List<ProductForecast> productForecastList, Integer userId) throws DAOException;

    String deleteForecastEntry(List<Integer> productForecastId) throws DAOException;
}
