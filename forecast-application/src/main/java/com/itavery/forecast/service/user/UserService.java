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
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;

public interface UserService {

    UserDTO createUser(RegistrationDTO userDto) throws ServiceException;

    UserDTO findUser(Integer userId) throws ServiceException;

    void updateUser(User user, Integer userId) throws ServiceException;

    Boolean requestedPasswordMatchesCurrentPassword(Integer userId, String requestedPw) throws ServiceException;

    void changePassword(Integer userId, String oldPassword, String newPassword) throws ServiceException;

    String deactivateUser(Integer userId) throws ServiceException;
}
