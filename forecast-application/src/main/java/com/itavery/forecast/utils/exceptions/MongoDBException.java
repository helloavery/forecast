package com.itavery.forecast.utils.exceptions;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/28/19
 * https://github.com/helloavery
 */

public class MongoDBException extends RuntimeException {

    public MongoDBException(String message) {
        super(message);
    }

}
