package com.itavery.forecast.dao.verification;

import com.itavery.forecast.constants.AccountStatusType;
import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.domain.mithra.annotation.Transactional;
import com.itavery.forecast.domain.mithra.user.AccountStatusDB;
import com.itavery.forecast.domain.mithra.user.AccountStatusDBFinder;
import com.itavery.forecast.domain.mithra.user.EmailTokenDB;
import com.itavery.forecast.domain.mithra.user.EmailTokenDBFinder;
import com.itavery.forecast.exceptions.DAOException;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-04-29
 * https://github.com/helloavery
 */

@Repository
public class VerificationDAOImpl implements VerificationDAO, Constants {

    private static final Logger LOGGER = LogManager.getLogger(VerificationDAOImpl.class);

    @Override
    @Transactional
    public void storeToken(String token, String email){
        try{
            EmailTokenDB emailTokenDB = new EmailTokenDB();
            emailTokenDB.setEmail(email);
            emailTokenDB.setToken(token);
            emailTokenDB.insert();
        }
        catch(Exception e){
            LOGGER.error("");
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "");
        }
    }

    @Override
    public String retrieveEmail(String token){
        try{
            EmailTokenDB emailTokenDB = EmailTokenDBFinder.findOne(EmailTokenDBFinder.token().eq(token));
            if(emailTokenDB == null){
                LOGGER.error("Could not locate email with token {}", token);
                throw DAOException.buildResponse(HttpStatus.SC_NOT_FOUND, "Verification token did not match against any e-mail");
            }
            return emailTokenDB.getEmail();
        }
        catch(Exception e){
            LOGGER.error("Error attempting to fetch email");
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Error attempting to fetch email");
        }
    }

    @Override
    @Transactional
    public String updateAccountStatus(String email) throws DAOException {
        try {
            AccountStatusDB accountStatusDB = AccountStatusDBFinder.findOne(AccountStatusDBFinder.user().email().eq(email));
            if (accountStatusDB != null) {
                Character status = accountStatusDB.getStatus();
                if (status.equals(AccountStatusType.ACTIVE.getCode())) {
                    accountStatusDB.setEmailVerified(true);
                    accountStatusDB.setActiveAndVerified(true);
                    return VERIFICATION_EMAIL_VERIFICATION_SUCCESSFUL;
                }
            }
            LOGGER.error("No verification token found");
            throw DAOException.buildResponse(HttpStatus.SC_NOT_FOUND, "Verification token not valid");
        } catch (DAOException e) {
            LOGGER.error("Could not verify email address for email {}", email);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Verification DAO Error: could not verify user email");
        }
    }
}
