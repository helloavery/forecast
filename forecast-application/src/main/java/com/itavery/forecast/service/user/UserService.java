package com.itavery.forecast.service.user;

import com.itavery.forecast.request.LoginRequest;
import com.itavery.forecast.request.RegistrationRequest;
import com.itavery.forecast.user.User;
import com.itavery.forecast.utils.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-30
 * https://github.com/helloavery
 */

public interface UserService {

    Response createUser(HttpServletRequest request, RegistrationRequest registrationRequest) throws ServiceException;

    Response verifyAuthyUser(HttpServletRequest request, String code);

    Response login(HttpServletRequest request, LoginRequest loginRequest);

    Response verifyOtp(HttpServletRequest request, String code);

    Response findUser(int userId) throws ServiceException;

    Response updateUser(User user, int userId) throws ServiceException;

    Response deactivateUser(int userId) throws ServiceException;
}
