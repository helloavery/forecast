package com.itavery.forecast.service.verification;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/22/18            
 |            
 *===========================================================================*/

public interface VerificationService {

    String generateToken(String email);

    void verifyEmailAddress(String token);
}
