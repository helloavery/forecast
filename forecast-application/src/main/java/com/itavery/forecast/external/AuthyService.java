package com.itavery.forecast.external;

import com.authy.AuthyException;
import com.itavery.forecast.AuthyOtpMethod;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.UserDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-10
 * https://github.com/helloavery
 */

public interface AuthyService {

    Integer createAuthyUser(RegistrationDTO user) throws AuthyException;

    boolean verifyAuthyUser(HttpServletRequest request, String code, int authyUserId) throws AuthyException;

    boolean requestAuthyOTP(HttpServletRequest request, UserDTO userDTO, AuthyOtpMethod authyOtpMethod);

    boolean verifyAuthyOTP(String token, int authyId);
}
