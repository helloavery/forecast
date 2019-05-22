package com.itavery.forecast.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itavery.forecast.AuthyOtpMethod;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-26
 * https://github.com/helloavery
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoginDTO {

    private String email;
    private String countryCode;
    private String phoneNumber;
    private AuthyOtpMethod authyOtpMethod;


    public String getEmail() {
        return email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AuthyOtpMethod getAuthyOtpMethod() {
        return authyOtpMethod;
    }

}
