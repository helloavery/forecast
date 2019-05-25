package com.itavery.forecast.service.user;

import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.user.LoginDTO;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-30
 * https://github.com/helloavery
 */

public interface UserService {

    Response createUser(HttpServletRequest request, RegistrationDTO userDto) throws ServiceException;

    Response verifyAuthyUser(HttpServletRequest request, String code);

    Response login(HttpServletRequest request, LoginDTO user);

    Response verifyOtp(HttpServletRequest request, String code);

    Response findUser(Integer userId) throws ServiceException;

    Response updateUser(User user, Integer userId) throws ServiceException;

    Response deactivateUser(Integer userId) throws ServiceException;
}
