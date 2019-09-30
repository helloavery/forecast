package com.itavery.forecast.utils.exceptions;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-03-27
 * https://github.com/helloavery
 */

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
