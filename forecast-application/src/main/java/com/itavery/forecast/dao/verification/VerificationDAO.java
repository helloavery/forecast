package com.itavery.forecast.dao.verification;

import com.itavery.forecast.utils.exceptions.DAOException;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-04-29
 * https://github.com/helloavery
 */

public interface VerificationDAO {

    void storeToken(String token, String email);

    String retrieveEmail(String token);

    String updateAccountStatus(String email) throws DAOException;
}
