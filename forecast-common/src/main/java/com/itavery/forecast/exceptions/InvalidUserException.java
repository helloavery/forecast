package com.itavery.forecast.exceptions;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-03-27
 * https://github.com/helloavery
 */

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }
}
