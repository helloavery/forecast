package com.itavery.forecast.dao.verification;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  4/29/18
 |
 *===========================================================================*/

import com.itavery.forecast.exceptions.DAOException;

public interface VerificationDAO {

    void storeToken(String token, String email);

    String retrieveEmail(String token);

    String updateAccountStatus(String email) throws DAOException;

    //This is a comment
}
