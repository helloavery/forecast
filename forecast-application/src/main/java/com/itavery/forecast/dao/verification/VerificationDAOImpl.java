package com.itavery.forecast.dao.verification;

import com.itavery.forecast.Constants;
import com.itavery.forecast.domain.adaptors.AccountStatusAdaptor;
import com.itavery.forecast.domain.adaptors.EmailTokenAdaptor;
import com.itavery.forecast.domain.mongodb.MongoDBBase;
import com.itavery.forecast.functional.AccountStatusType;
import com.itavery.forecast.user.AccountStatus;
import com.itavery.forecast.user.EmailToken;
import com.itavery.forecast.utils.exceptions.DAOException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-04-29
 * https://github.com/helloavery
 */

@Repository
public class VerificationDAOImpl implements VerificationDAO {

    private static final Logger LOGGER = LogManager.getLogger(VerificationDAOImpl.class);

    private MongoDBBase mongoDBBase;

    @Inject
    public void setMongoDBBase(MongoDBBase mongoDBBase) {
        this.mongoDBBase = mongoDBBase;
    }

    @Override
    public void storeToken(String token, String email){
        try {
            EmailToken emailToken = new EmailToken();
            emailToken.setEmail(email);
            emailToken.setToken(token);
            mongoDBBase.insertDocument(Constants.EMAIL_TOKEN_MONGO_COLLECTION, EmailTokenAdaptor.toDBObject(emailToken));
        } catch (Exception e) {
            LOGGER.error("Could not store token for email {}", email);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Verification DAO Error: Could not store token for email");
        }
    }

    @Override
    public String retrieveEmail(String token){
        try {
            EmailToken emailToken = mongoDBBase.getDocumentByKey(EmailToken.class, Constants.EMAIL_TOKEN_MONGO_COLLECTION, "token", token);
            return emailToken.getEmail();
        } catch (Exception e) {
            LOGGER.error("Could not retrieve email address using token {}", token);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Verification DAO Error: could not find email");
        }
    }

    @Override
    public String updateAccountStatus(String email) throws DAOException {
        try {
            AccountStatus oldAccountStatus = mongoDBBase.getDocumentById(AccountStatus.class, Constants.ACCOUNT_STATUS_MONGO_COLLECTION, email);
            if(oldAccountStatus != null){
                char status =  oldAccountStatus.getStatus();
                if(status  == AccountStatusType.ACTIVE.getCode()){
                    AccountStatus accountStatus = new AccountStatus();
                    accountStatus.setEmailVerified(BooleanUtils.toInteger(true));
                    accountStatus.setActiveAndVerified(BooleanUtils.toInteger(true));
                    mongoDBBase.updateDocument(Constants.ACCOUNT_STATUS_MONGO_COLLECTION,
                            AccountStatusAdaptor.toDBObject(oldAccountStatus), AccountStatusAdaptor.toDBObject(accountStatus));
                    return Constants.VERIFICATION_EMAIL_VERIFICATION_SUCCESSFUL;
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
