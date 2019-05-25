package com.itavery.forecast.service.verification;


import javax.ws.rs.core.Response;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-22
 * https://github.com/helloavery
 */

public interface VerificationService {

    String generateToken(String email);

    Response verifyEmailAddress(String token);
}
