package com.itavery.forecast.dao.verification;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  4/29/18
 |
 *===========================================================================*/

import com.itavery.forecast.OperationResult;
import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.mithra.annotation.Transactional;
import com.itavery.forecast.mithra.user.AccountStatusDB;
import com.itavery.forecast.mithra.user.AccountStatusDBFinder;
import com.itavery.forecast.mithra.user.EmailTokenDB;
import com.itavery.forecast.mithra.user.EmailTokenDBFinder;
import com.itavery.forecast.user.AccountStatusType;
import com.itavery.forecast.BooleanToIntAdaptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VerificationDAOImpl implements VerificationDAO {

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
            throw new RuntimeException();
        }
    }

    @Override
    public String retrieveEmail(String token){
        String email;
        try{
            EmailTokenDB emailTokenDB = EmailTokenDBFinder.findOne(EmailTokenDBFinder.token().eq(token));
            if(emailTokenDB == null){
                LOGGER.error("Could not locate email with token {}", token);
                throw new RuntimeException();
            }
            email = emailTokenDB.getEmail();
        }
        catch(Exception e){
            LOGGER.error("Error attempting to fetch email");
            throw new RuntimeException("Error attempting to fetch email", e);
        }
        return email;
    }

    @Override
    @Transactional
    public String updateAccountStatus(String email) throws DAOException {
        String returnMessage = null;
        try {
            AccountStatusDB accountStatusDB = AccountStatusDBFinder.findOne(AccountStatusDBFinder.accountStatuses().email().eq(email));
            if (accountStatusDB != null) {
                Character status = accountStatusDB.getStatus();
                if (status.equals(AccountStatusType.ACTIVE.getCode())) {
                    accountStatusDB.setEmailVerified(BooleanToIntAdaptor.TRUE.getValue());
                    accountStatusDB.setActiveAndVerified(BooleanToIntAdaptor.TRUE.getValue());
                    returnMessage = OperationResult.VERIFICATION_EMAIL_VERIFICATION_SUCCESSFUL.getMessage();
                }
            } else {
                LOGGER.error("No verification token found");
                returnMessage = OperationResult.DAO_TECHNICAL_ERROR.getMessage();
            }
        } catch (DAOException e) {
            LOGGER.error("Could not verify email address for email", email);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Verification DAO Error: could not verify user email");
        }
        return returnMessage;
    }
}
