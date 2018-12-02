package com.itavery.forecast.external;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/1/18            
 |            
 *===========================================================================*/


import com.mashape.unirest.http.JsonNode;

public interface MailgunEmailVerification {

    JsonNode validateEmail(String email) throws Exception;
}
