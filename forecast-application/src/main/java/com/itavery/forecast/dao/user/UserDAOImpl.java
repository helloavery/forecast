package com.itavery.forecast.dao.user;

import com.itavery.forecast.assemblers.UserAssembler;
import com.itavery.forecast.constants.AccountStatusType;
import com.itavery.forecast.constants.OperationResult;
import com.itavery.forecast.constants.Regions;
import com.itavery.forecast.constants.RoleValues;
import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.mithra.annotation.Transactional;
import com.itavery.forecast.mithra.organization.RolesDBFinder;
import com.itavery.forecast.mithra.organization.RolesDBList;
import com.itavery.forecast.mithra.user.AccountStatusDB;
import com.itavery.forecast.mithra.user.AccountStatusDBFinder;
import com.itavery.forecast.mithra.user.UsersDB;
import com.itavery.forecast.mithra.user.UsersDBFinder;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-18
 * https://github.com/helloavery
 */

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    @Inject
    private UserAssembler userAssembler;
    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public UserDTO createUser(RegistrationDTO registrationDTO, Integer authyId) throws DAOException {
        UserDTO registeredUser = null;
        try {
            UsersDB userToBeRegistered = userAssembler.covertToDB(registrationDTO);
            AccountStatusDB userToBeRegisteredAccountStatus = new AccountStatusDB(AccountStatusType.ACTIVE.getCode(), false, false);
            RolesDBList defaultRoleList = RolesDBFinder.findMany(RolesDBFinder.role().eq(RoleValues.USER.getUserRoleValue()));
            userToBeRegistered.setAuthyUserId(authyId);
            userToBeRegistered.setRegion(Regions.AMERICAS.getCode());
            userToBeRegistered.setAccountStatus(userToBeRegisteredAccountStatus);
            userToBeRegistered.setRoles(defaultRoleList);
            registeredUser = userAssembler.covertToDTO(userToBeRegistered);
            userToBeRegistered.cascadeInsert();
            LOGGER.info("Successfully inserted new user data in DB for {} : {}", registrationDTO.getUsername(),registrationDTO.getEmail());
            //////////////Return the generated verificationId///////
        } catch (DAOException e) {
            LOGGER.error("Could not create user account with username {} and email {}", registrationDTO.getUsername(), registrationDTO.getEmail());
            LOGGER.error(e.getMessage(), e);
        }
        return registeredUser;
    }

    @Override
    public UserDTO findUser(Integer userId) throws DAOException {
        UserDTO userDTO = null;
        try {
            UsersDB user = fetchUserByUserId(userId);
            if (user != null) {
                userDTO = userAssembler.covertToDTO(user);
            } else {
                LOGGER.error("User not found with username {}", userId);
            }
        } catch (DAOException e) {
            LOGGER.error("Could not find user for username {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("User DAO Error: could not find user");
        }
        return userDTO;
    }

    @Override
    public UserDTO findUser(String email) throws DAOException {
        UserDTO userDTO = null;
        try {
            UsersDB user = fetchUserByEmail(email);
            if (user != null) {
                userDTO = userAssembler.covertToDTO(user);
            } else {
                LOGGER.error("User not found with email {}", email);
            }
        } catch (DAOException e) {
            LOGGER.error("Could not find user for email {}", email);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("User DAO Error: could not find user");
        }
        return userDTO;
    }

    @Override
    @Transactional
    public void updateUser(User user) throws DAOException {
        try {
            UsersDB userDB = fetchUserByUserId(user.getUserId());
            if (userDB != null) {
                if (user.getFirstName() != null) {
                    userDB.setFirstName(user.getFirstName());
                }
                if (user.getLastName() != null) {
                    userDB.setLastName(user.getLastName());
                }
                if (user.getUsername() != null) {
                    userDB.setUsername(user.getUsername());
                }
                if (user.getEmail() != null) {
                    userDB.setEmail(user.getEmail());
                }
                if (user.getCountryCode() != null) {
                    userDB.setCountryCode(user.getCountryCode());
                }
                if (user.getPhoneNumber() != null) {
                    userDB.setPhoneNumber(user.getPhoneNumber());
                }
                if (user.getRegion() != null) {
                    userDB.setRegion(user.getRegion());
                }
            }
        } catch (DAOException e) {
            LOGGER.error("Could not update user for user {}", user.getUserId());
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("User DAO Error: could not update user");
        }
    }

    @Override
    @Transactional
    public String deactivateUser(Integer userId) throws DAOException {
        String returnMessage;
        try {
            LOGGER.info("Setting deactivation status for user {}", userId);
            AccountStatusDB accountStatusDB = fetchAccountStatusByUserId(userId);
            if (accountStatusDB != null) {
                accountStatusDB.setStatus(AccountStatusType.DEACTIVATED.getCode());
                accountStatusDB.setActiveAndVerified(false);
            }
            returnMessage = OperationResult.USER_SUCCESSFUL_DEACTIVATION.getMessage();
        } catch (DAOException e) {
            LOGGER.error("Could not deactivate user {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("User DAO Error: could not deactivate user");
        }
        return returnMessage;
    }

    private UsersDB fetchUserByUserId(Integer userId) {
        return UsersDBFinder.findOne(UsersDBFinder.userId().eq(userId));
    }

    private UsersDB fetchUserByEmail(String email) {
        return UsersDBFinder.findOne(UsersDBFinder.email().toLowerCase().eq(email.toLowerCase()));
    }

    private AccountStatusDB fetchAccountStatusByUserId(Integer userId) {
        return AccountStatusDBFinder.findOne(AccountStatusDBFinder.userId().eq(userId));
    }
}
