package com.itavery.forecast.external;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/1/18            
 |            
 *===========================================================================*/


public interface MailgunEmailVerification {

    EmailValidationResponse validateEmail(String email) throws Exception;
}
