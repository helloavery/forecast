package com.itavery.forecast.external;

import com.authy.AuthyException;
import com.itavery.forecast.functional.AuthyOtpMethod;
import com.itavery.forecast.response.UserResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-10
 * https://github.com/helloavery
 */

public interface AuthyService {

    Integer createAuthyUser(String email, String countryCode, String phoneNumber) throws AuthyException;

    boolean verifyAuthyUser(HttpServletRequest request, String code, int authyUserId) throws AuthyException;

    boolean requestAuthyOTP(HttpServletRequest request, UserResponse userDTO, AuthyOtpMethod authyOtpMethod);

    boolean verifyAuthyOTP(String token, int authyId);
}
