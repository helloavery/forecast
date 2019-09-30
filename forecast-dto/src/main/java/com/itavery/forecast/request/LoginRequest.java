package com.itavery.forecast.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itavery.forecast.functional.AuthyOtpMethod;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/27/19
 * https://github.com/helloavery
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest extends BaseUserRequest {

    @NotNull
    @FormParam("authyOTPMethod")
    private AuthyOtpMethod authyOTPMethod;

    public AuthyOtpMethod getAuthyOTPMethod() {
        return authyOTPMethod;
    }

    public void setAuthyOTPMethod(AuthyOtpMethod authyOTPMethod) {
        this.authyOTPMethod = authyOTPMethod;
    }
}
