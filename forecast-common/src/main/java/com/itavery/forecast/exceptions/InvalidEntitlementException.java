package com.itavery.forecast.exceptions;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-25
 * https://github.com/helloavery
 */

public class InvalidEntitlementException extends RuntimeException{

    public InvalidEntitlementException(String message){
        super(message);
    }
}
