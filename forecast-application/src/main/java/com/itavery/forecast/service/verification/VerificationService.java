package com.itavery.forecast.service.verification;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-22
 * https://github.com/helloavery
 */

public interface VerificationService {

    String generateToken(String email);

    void verifyEmailAddress(String token);
}
