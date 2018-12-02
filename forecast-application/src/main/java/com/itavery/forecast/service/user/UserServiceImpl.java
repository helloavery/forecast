package com.itavery.forecast.service.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  1/30/18
 |
 *===========================================================================*/

import com.itavery.forecast.ForecastConstants;
import com.itavery.forecast.audit.AuditType;
import com.itavery.forecast.dao.user.UserDAO;
import com.itavery.forecast.exceptions.InvalidInputException;
import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.service.email.EmailService;
import com.itavery.forecast.service.verification.VerificationService;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;
import com.itavery.forecast.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final AuditService auditService;
    private final UserDAO userDAO;
    private UserValidator userValidator;
    private EmailService emailService;
    private VerificationService verificationService;

    public UserServiceImpl(final AuditService auditService, final UserDAO userDAO, UserValidator userValidator, EmailService emailService, VerificationService verificationService) {
        this.auditService = auditService;
        this.userDAO = userDAO;
        this.userValidator = userValidator;
        this.emailService = emailService;
        this.verificationService = verificationService;
    }

    @Override
    public UserDTO createUser(RegistrationDTO registrationDTO) throws ServiceException {
        UserDTO userDTO = new UserDTO();
        try {
            LOGGER.info("Validating registration inputs");
            userValidator.validate(registrationDTO.getEmail(), registrationDTO.getPassword(), registrationDTO.getFirstName(), registrationDTO.getLastName());
            LOGGER.info("Attempting to create user");
            userDTO = userDAO.createUser(registrationDTO);
            if (userDTO == null) {
                LOGGER.error("Error creating user");
                throw new ServiceException("Service.USER_NOT_CREATED");
            } else {
                String emailToken = verificationService.generateToken(registrationDTO.getEmail());
                emailService.sendEmailAddressVerificationEmail(registrationDTO.getEmail(), registrationDTO.getFirstName(), emailToken);
                auditService.createAudit(registrationDTO.getUsername(), AuditType.ACCOUNT_CREATED, null);
                auditService.createAudit(registrationDTO.getUsername(), AuditType.ACCOUNT_ACTIVATED, null);
                LOGGER.info("Successfully completed addition of new user {} : {}", registrationDTO.getUsername(),registrationDTO.getEmail());
            }
        } catch (Exception e) {

        }
        return userDTO;
    }

    @Override
    public UserDTO findUser(Integer userId) throws ServiceException {
        LOGGER.info("Attempting to find user {}", userId);
        UserDTO userDto = userDAO.findUser(userId);
        if (userDto == null) {
            LOGGER.error("Error finding user {}", userId);
            throw new ServiceException("Service.USER_NOT_FOUND");
        }
        return userDto;
    }

    @Override
    public void updateUser(User user, Integer userId) throws ServiceException {
        try {
            LOGGER.info("Validating updated user information for {}", userId);
            userValidator.validate(user.getEmail(), null, user.getFirstName(), user.getLastName());
            LOGGER.info("Attempting to update user {}", userId);
            userDAO.updateUser(user);
            //TODO: Implement Audit Service
            auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ACCOUNT_UPDATED, null);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not create audit event for user update for {}", userId);
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public Boolean requestedPasswordMatchesCurrentPassword(Integer userId, String requestedPw) throws ServiceException {
        LOGGER.info("Verifying new desired password does not match old password");
        Boolean success = false;
        try {
            LOGGER.info("Attempting to validate passwords");
            success = userDAO.requestedPasswordMatchesCurrentPassword(userId, requestedPw);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not validate old and new passwords");
                LOGGER.error(e.getMessage(), e);
            }
        }
        return success;
    }

    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) throws ServiceException {
        LOGGER.info("Changing password for user {}", userId);
        try {
            LOGGER.info("Validating new desired password");
            if (oldPassword.equalsIgnoreCase(newPassword)) {
                throw new InvalidInputException("Service.SAME_NEW_PASSWORD");
            } else {
                userValidator.isValidPassword(newPassword);
                LOGGER.error("Attempting to change password {}", userId);
                String email = userDAO.changePassword(userId, oldPassword, newPassword);
                if (email == null) {
                    LOGGER.error("Could not retrieve e-mail during password change for userId {}", userId);
                } else {
                    //TODO: Implement Audit Service
                    emailService.sendPasswordChangeEmail(email, null);
                    auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ACCOUNT_UPDATED, null);
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not change password for user {}", userId);
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String deactivateUser(Integer userId) throws ServiceException {
        String returnMessage = null;
        try {
            LOGGER.info("Attempting to deactivate user {}", userId);
            returnMessage = userDAO.deactivateUser(userId);
            //TODO: Implement Audit Service
            auditService.createAudit(ForecastConstants.USERID_PREFIX + userId, AuditType.ACCOUNT_DEACTIVATED, null);
        } catch (Exception e) {
            if (e.getMessage().contains("Service")) {
                LOGGER.error("Could not deactivate user {}", userId);
                LOGGER.error(e.getMessage(), e);
            }
        }
        return returnMessage;
    }
}
