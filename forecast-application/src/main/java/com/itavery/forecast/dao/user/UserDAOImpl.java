package com.itavery.forecast.dao.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  2/18/18
 |
 *===========================================================================*/


import com.itavery.forecast.OperationResult;
import com.itavery.forecast.assemblers.UserAssembler;
import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.mithra.annotation.Transactional;
import com.itavery.forecast.mithra.organization.RolesDB;
import com.itavery.forecast.mithra.organization.RolesDBFinder;
import com.itavery.forecast.mithra.organization.RolesDBList;
import com.itavery.forecast.mithra.user.AccountStatusDB;
import com.itavery.forecast.mithra.user.AccountStatusDBFinder;
import com.itavery.forecast.mithra.user.UserCredsDB;
import com.itavery.forecast.mithra.user.UserCredsDBFinder;
import com.itavery.forecast.mithra.user.UserRolesDB;
import com.itavery.forecast.mithra.user.UsersDB;
import com.itavery.forecast.mithra.user.UsersDBFinder;
import com.itavery.forecast.regions.Regions;
import com.itavery.forecast.user.AccountStatusType;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.RoleValues;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;


public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private final UserAssembler userAssembler;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDAOImpl(final UserAssembler userAssembler, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userAssembler = userAssembler;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDTO createUser(RegistrationDTO registrationDTO) throws DAOException {
        UserDTO registeredUser = null;
        try {
            UsersDB userToBeRegistered = userAssembler.covertToDB(registrationDTO);
            UserCredsDB userToBeRegisteredCredentials = new UserCredsDB();
            AccountStatusDB userToBeRegisteredAccountStatus = new AccountStatusDB();
            UserRolesDB userRolesDB = new UserRolesDB();
            userToBeRegisteredCredentials.setPassword(bCryptPasswordEncoder.encode(registrationDTO.getPassword()));
            userToBeRegisteredCredentials.setCreatedDate(new Timestamp(new Date().getTime()));
            userToBeRegisteredCredentials.setTimesModified(1);
            userToBeRegisteredAccountStatus.setEmailVerified(false);
            userToBeRegisteredAccountStatus.setStatus(AccountStatusType.ACTIVE.getCode());
            userToBeRegisteredAccountStatus.setActiveAndVerified(false);
            RolesDBList defaultRoleList = RolesDBFinder.findMany(RolesDBFinder.role().eq(RoleValues.USER.getUserRoleValue()));
            RolesDB defaultRole = RolesDBFinder.findOne(RolesDBFinder.role().eq(RoleValues.USER.getUserRoleValue()));
            userToBeRegistered.setFirstName(registrationDTO.getFirstName());
            userToBeRegistered.setLastName(registrationDTO.getLastName());
            userToBeRegistered.setUsername(registrationDTO.getUsername());
            userToBeRegistered.setEmail(registrationDTO.getEmail());
            userToBeRegistered.setCountryCode(registrationDTO.getCountryCode());
            userToBeRegistered.setPhoneNumber(registrationDTO.getPhoneNumber());
            userToBeRegistered.setRegion(Regions.AMERICAS.getCode());
            userToBeRegistered.setUserCredentials(userToBeRegisteredCredentials);
            userToBeRegistered.setAccountStatus(userToBeRegisteredAccountStatus);
            userToBeRegistered.setRoles(defaultRoleList);
            userRolesDB.setRoles(defaultRole);
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
    public Boolean requestedPasswordMatchesCurrentPassword(Integer userId, String requestedPw) throws DAOException {
        try {
            UserCredsDB userCredsDB = fetchUserCredsByUserId(userId);
            return userCredsDB.getPassword().equals(requestedPw);
        } catch (DAOException e) {
            LOGGER.error("Could not verify old and new password for {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("User DAO Error: could not verify password inputs");
        }
    }

    @Override
    @Transactional
    public String changePassword(Integer userId, String oldPassword, String newPassword) throws DAOException {
        String email = null;
        try {
            LOGGER.info("Updating password for user {}", userId);
            UserCredsDB userCredsDB = fetchUserCredsByUserId(userId);
            if (userCredsDB != null) {
                userCredsDB.setPassword(newPassword);
                userCredsDB.setTimesModified(userCredsDB.getTimesModified() + 1);
                email = userCredsDB.getUserCredentials().getEmail();
            }
            //TODO:SEND E-MAIL OF PASSWORD CHANGE
        } catch (DAOException e) {
            LOGGER.error("Could not change password for user {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("User DAO Error: could not change password");
        }
        return email;
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

    private UserCredsDB fetchUserCredsByUserId(Integer userId) {
        return UserCredsDBFinder.findOne(UserCredsDBFinder.userId().eq(userId));
    }

    private AccountStatusDB fetchAccountStatusByUserId(Integer userId) {
        return AccountStatusDBFinder.findOne(AccountStatusDBFinder.userId().eq(userId));
    }
}
