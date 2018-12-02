package com.itavery.forecast.service.email;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  7/19/18            
 |            
 *===========================================================================*/


public interface EmailService {

    void sendEmailAddressVerificationEmail(String emailAddress, String name, String emailToken) throws Exception;

    void sendPasswordChangeEmail(String emailAddress, String name) throws Exception;

    EmailContent getVerificationEmail(String emailAddress, String name, String emailToken, String environment);

    EmailContent getPasswordChangeEmail(String emailAddress, String name);

}
