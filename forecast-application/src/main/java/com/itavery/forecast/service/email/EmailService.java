package com.itavery.forecast.service.email;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-07-19
 * https://github.com/helloavery
 */

public interface EmailService {

    void sendEmailAddressVerificationEmail(String contextUrl, String emailAddress, String name, String emailToken) throws Exception;

    void sendPasswordChangeEmail(String contextUrl, String emailAddress, String name) throws Exception;

    EmailContent getVerificationEmail(String emailAddress, String name, String emailToken, String environment);

    EmailContent getPasswordChangeEmail(String emailAddress, String name);

}
