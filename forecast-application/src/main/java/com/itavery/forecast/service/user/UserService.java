package com.itavery.forecast.service.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  1/30/18
 |
 *===========================================================================*/

import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.user.LoginDTO;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    UserDTO createUser(HttpServletRequest request, RegistrationDTO userDto) throws ServiceException;

    boolean verifyAuthyUser(HttpServletRequest request, String code);

    boolean login(HttpServletRequest request, LoginDTO user);

    boolean verifyOtp(HttpServletRequest request, String code);

    UserDTO findUser(Integer userId) throws ServiceException;

    void updateUser(User user, Integer userId) throws ServiceException;

    String deactivateUser(Integer userId) throws ServiceException;
}
