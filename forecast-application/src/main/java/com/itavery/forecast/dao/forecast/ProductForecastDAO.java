package com.itavery.forecast.dao.forecast;

import com.itavery.forecast.request.ProductForecastRequest;
import com.itavery.forecast.response.ProductForecastResponse;
import com.itavery.forecast.utils.exceptions.DAOException;

import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-10
 * https://github.com/helloavery
 */

public interface ProductForecastDAO {

    String addForecastEntry(Integer userId, ProductForecastRequest pfRequest) throws DAOException;

    List<ProductForecastResponse> getForecastEntries(Integer userId) throws DAOException;

    String updateForecastEntries(List<ProductForecastRequest> productForecastList, Integer userId) throws DAOException;

    String deleteForecastEntry(List<Integer> productForecastId) throws DAOException;
}
