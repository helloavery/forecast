package com.itavery.forecast.exceptions;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-03-27
 * https://github.com/helloavery
 */

public class DAOException extends RuntimeException {

    public DAOException(String message) {
        super(message);
    }
}
