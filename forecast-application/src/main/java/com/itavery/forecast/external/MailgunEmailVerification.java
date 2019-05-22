package com.itavery.forecast.external;

import com.mashape.unirest.http.JsonNode;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-01
 * https://github.com/helloavery
 */

public interface MailgunEmailVerification {

    JsonNode validateEmail(String email) throws Exception;
}
