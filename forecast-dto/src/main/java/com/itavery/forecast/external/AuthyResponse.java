package com.itavery.forecast.external;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-13
 * https://github.com/helloavery
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class AuthyResponse {

    private String message;
    private Object errors;
    private boolean success;
    private String token;
    private String device;

    public boolean otpSuccessfullySent(){
        return errors == null && success;
    }
}
