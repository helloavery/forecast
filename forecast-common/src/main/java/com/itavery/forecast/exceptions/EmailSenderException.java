package com.itavery.forecast.exceptions;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-07-21
 * https://github.com/helloavery
 */

public class EmailSenderException extends RuntimeException {

    public EmailSenderException(String message) {
        super(message);
    }
}
