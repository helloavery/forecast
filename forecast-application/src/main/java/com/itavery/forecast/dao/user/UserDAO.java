package com.itavery.forecast.dao.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  2/18/18
 |
 *===========================================================================*/


import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;


public interface UserDAO {

    UserDTO createUser(RegistrationDTO registrationDTO, Integer authyId) throws DAOException;

    UserDTO findUser(Integer userId) throws DAOException;

    UserDTO findUser(String email) throws DAOException;

    void updateUser(User user) throws DAOException;

    String deactivateUser(Integer userId) throws DAOException;
}
