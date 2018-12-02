package com.itavery.forecast.exceptions;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  7/21/18            
 |            
 *===========================================================================*/

public class EmailSenderException extends RuntimeException {

    public EmailSenderException(String message) {
        super(message);
    }
}
