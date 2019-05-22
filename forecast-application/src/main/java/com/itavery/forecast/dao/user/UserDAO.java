package com.itavery.forecast.dao.user;

import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-18
 * https://github.com/helloavery
 */

public interface UserDAO {

    UserDTO createUser(RegistrationDTO registrationDTO, Integer authyId) throws DAOException;

    UserDTO findUser(Integer userId) throws DAOException;

    UserDTO findUser(String email) throws DAOException;

    void updateUser(User user) throws DAOException;

    String deactivateUser(Integer userId) throws DAOException;
}
