package com.itavery.forecast.exceptions;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  3/27/18
 |
 *===========================================================================*/

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
