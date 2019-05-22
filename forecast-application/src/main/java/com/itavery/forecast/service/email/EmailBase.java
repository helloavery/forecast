package com.itavery.forecast.service.email;

import com.itavery.forecast.InstanceEnvironments;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-21
 * https://github.com/helloavery
 */

abstract class EmailBase {

    protected String getForecasterBaseUrl(String environment){
        return InstanceEnvironments.getInstance(environment);
    }
}
