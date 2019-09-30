package com.itavery.forecast.dao.user;

import com.itavery.forecast.request.RegistrationRequest;
import com.itavery.forecast.response.UserResponse;
import com.itavery.forecast.user.User;
import com.itavery.forecast.utils.exceptions.DAOException;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-18
 * https://github.com/helloavery
 */

public interface UserDAO {

    int createUser(RegistrationRequest regRequest, Integer authyId) throws DAOException;

    UserResponse findUser(Integer userId) throws DAOException;

    UserResponse findUser(String email) throws DAOException;

    void updateUser(User user) throws DAOException;

    String deactivateUser(Integer userId) throws DAOException;
}
