package com.itavery.forecast.service.email;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/21/18            
 |            
 *===========================================================================*/

import com.itavery.forecast.InstanceEnvironments;

public abstract class EmailBase {

    protected String getForecasterBaseUrl(String environment){
        return InstanceEnvironments.getInstance(environment);
    }
}
