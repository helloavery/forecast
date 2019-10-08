package com.itavery.forecast.dao.user;

import com.averygrimes.nexus.pojo.RoleValues;
import com.itavery.forecast.Constants;
import com.itavery.forecast.domain.adaptors.AccountStatusAdaptor;
import com.itavery.forecast.domain.adaptors.UserAdaptor;
import com.itavery.forecast.domain.mongodb.MongoDBBase;
import com.itavery.forecast.functional.Regions;
import com.itavery.forecast.request.RegistrationRequest;
import com.itavery.forecast.response.UserResponse;
import com.itavery.forecast.user.AccountStatus;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserCreds;
import com.itavery.forecast.user.UserRole;
import com.itavery.forecast.utils.exceptions.DAOException;
import com.mongodb.DBObject;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-18
 * https://github.com/helloavery
 */

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private MongoDBBase mongoDBBase;

    @Inject
    public void setMongoDBBase(MongoDBBase mongoDBBase) {
        this.mongoDBBase = mongoDBBase;
    }

    @Override
    public int createUser(RegistrationRequest regRequest, Integer authyId) throws DAOException {
        try {
            User userToBeRegistered = UserAdaptor.toUserObject(regRequest, authyId, Regions.AMERICAS);
            AccountStatus userToBeRegisteredAccountStatus = AccountStatusAdaptor.createNewAccountStatusObject(userToBeRegistered);
            UserCreds userCreds = UserAdaptor.toNewUserCredsObject(regRequest);
            UserRole userRole = UserAdaptor.toNewUserRoleObject(userToBeRegistered, RoleValues.USER);

            DBObject userDBObject = UserAdaptor.toDBObject(userToBeRegistered);
            DBObject accountStatusDBObject = UserAdaptor.toDBObject(userToBeRegisteredAccountStatus);
            DBObject userCredsDBObject = UserAdaptor.toDBObject(userCreds);
            DBObject userRoleDBObject  = UserAdaptor.toDBObject(userRole);

            Map<String, List<DBObject>> documentsToInsert = new HashMap<>();
            documentsToInsert.put(Constants.USERS_MONGO_COLLECTION, Arrays.asList(userDBObject));
            documentsToInsert.put(Constants.ACCOUNT_STATUS_MONGO_COLLECTION, Arrays.asList(accountStatusDBObject));
            documentsToInsert.put(Constants.USER_CREDS_MONGO_COLLECTION, Arrays.asList(userCredsDBObject));
            documentsToInsert.put(Constants.USER_ROLES_MONGO_COLLECTION, Arrays.asList(userRoleDBObject));
            mongoDBBase.bulkInsertDocuments(documentsToInsert);
            LOGGER.info("Successfully inserted new user data in DB for {} : {}", regRequest.getUsername(),regRequest.getEmail());
            return userToBeRegistered.getUserId();
        } catch (DAOException e) {
            LOGGER.error("Could not create user account with username {} and email {}", regRequest.getUsername(), regRequest.getEmail());
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_NOT_FOUND, Constants.DAO_USER_NOT_CREATED);
        }
    }

    @Override
    public UserResponse findUser(Integer userId) throws DAOException {
        try {
            return mongoDBBase.getDocumentById(UserResponse.class, Constants.USERS_MONGO_COLLECTION, userId);
        } catch (DAOException e) {
            LOGGER.error("Could not find user for username {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, Constants.DAO_USER_NOT_FOUND);
        }
    }

    @Override
    public UserResponse findUser(String email) throws DAOException {
        try {
            return mongoDBBase.getDocumentByKey(UserResponse.class, Constants.USERS_MONGO_COLLECTION, "email", email);
        } catch (DAOException e) {
            LOGGER.error("Could not find user for email {}", email);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_NOT_FOUND, Constants.DAO_USER_NOT_FOUND);
        }
    }

    @Override
    public void updateUser(User user) throws DAOException {
        try {
            User oldUser = mongoDBBase.getDocumentById(User.class, Constants.USERS_MONGO_COLLECTION, user.getUserId());
            mongoDBBase.updateDocument(Constants.USERS_MONGO_COLLECTION, UserAdaptor.toDBObject(oldUser), UserAdaptor.toDBObject(user));
        } catch (DAOException e) {
            LOGGER.error("Could not update user for user {}", user.getUserId());
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, Constants.DAO_USER_NOT_UPDATED);
        }
    }

    @Override
    public String deactivateUser(Integer userId) throws DAOException {
        try {
            LOGGER.info("Setting deactivation status for user {}", userId);
            AccountStatus oldAccountStatus = mongoDBBase.getDocumentById(AccountStatus.class, Constants.ACCOUNT_STATUS_MONGO_COLLECTION, userId);
            AccountStatus updatedAccountStatus = AccountStatusAdaptor.createDeactivationStatusObject(userId);
            mongoDBBase.updateDocument(Constants.ACCOUNT_STATUS_MONGO_COLLECTION,
                    AccountStatusAdaptor.toDBObject(oldAccountStatus), AccountStatusAdaptor.toDBObject(updatedAccountStatus));
            return Constants.USER_SUCCESSFUL_DEACTIVATION;
        } catch (DAOException e) {
            LOGGER.error("Could not deactivate user {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, Constants.DAO_USER_NOT_DEACTIVATED);
        }
    }

}
