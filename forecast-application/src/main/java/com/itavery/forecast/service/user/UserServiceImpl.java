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
import com.itavery.forecast.SessionManager;
import com.itavery.forecast.audit.AuditType;
import com.itavery.forecast.dao.user.UserDAO;
import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.external.AuthyService;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.service.email.EmailService;
import com.itavery.forecast.service.verification.VerificationService;
import com.itavery.forecast.user.LoginDTO;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;
import com.itavery.forecast.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private SessionManager sessionManager;
    private final AuditService auditService;
    private final UserDAO userDAO;
    private UserValidator userValidator;
    private EmailService emailService;
    private VerificationService verificationService;
    private AuthyService authyService;

    @Inject
    public UserServiceImpl(SessionManager sessionManager, final AuditService auditService, final UserDAO userDAO, UserValidator userValidator, EmailService emailService,
                           VerificationService verificationService, AuthyService authyService) {
        this.sessionManager = sessionManager;
        this.auditService = auditService;
        this.userDAO = userDAO;
        this.userValidator = userValidator;
        this.emailService = emailService;
        this.verificationService = verificationService;
        this.authyService = authyService;
    }

    @Override
    public UserDTO createUser(HttpServletRequest request, RegistrationDTO registrationDTO) throws ServiceException {
        try {
            LOGGER.info("Validating registration inputs");
            userValidator.validate(registrationDTO.getEmail(), registrationDTO.getPassword(), registrationDTO.getFirstName(), registrationDTO.getLastName());
            LOGGER.info("Attempting to create user");
            Integer authyId = authyService.createAuthyUser(registrationDTO);
            if(authyId == null){
                LOGGER.error("Could not create authy user using e-mail {} and phone number {}-{}", registrationDTO.getEmail(),registrationDTO.getCountryCode(),registrationDTO.getPhoneNumber());
                throw new ServiceException("Service.AUTHY_USER_NOT_CREATED");
            }
            UserDTO userDTO = userDAO.createUser(registrationDTO, authyId);
            if (userDTO == null) {
                LOGGER.error("Error creating user");
                throw new ServiceException("Service.USER_NOT_CREATED");
            }
            String emailToken = verificationService.generateToken(registrationDTO.getEmail());
            emailService.sendEmailAddressVerificationEmail(registrationDTO.getEmail(), registrationDTO.getFirstName(), emailToken);
            auditService.createAudit(registrationDTO.getUsername(), AuditType.ACCOUNT_CREATED, null);
            auditService.createAudit(registrationDTO.getUsername(), AuditType.ACCOUNT_ACTIVATED, null);
            sessionManager.partialLogIn(request, userDTO.getUserId());
            LOGGER.info("Successfully completed addition of new user {} : {}", registrationDTO.getUsername(),registrationDTO.getEmail());
            return userDTO;
        } catch (Exception e) {
            LOGGER.error("Service: Could not register user", e);
            throw new ServiceException("Service.CANNOT_REGISTER_USER");
        }
    }

    @Override
    public boolean verifyAuthyUser(HttpServletRequest request, String code){
        try{
            int userId = sessionManager.getLoggedUserId(request);
            UserDTO userDTO = userDAO.findUser(userId);
            int authyUserId = userDTO.getAuthyUserId();
            return authyService.verifyAuthyUser(request, code, authyUserId);
        }
        catch(Exception e){
            LOGGER.error("Service: Could not verify Authy user", e);
            throw new ServiceException("Service.CANNOT_VERIFY_AUTHY_USER");
        }
    }

    @Override
    public boolean login(HttpServletRequest request, LoginDTO user){
        try{
            UserDTO userDTO = userDAO.findUser(user.getEmail());
            return authyService.requestAuthyOTP(request, userDTO, user.getAuthyOtpMethod());
        }
        catch(Exception e){
            LOGGER.error("Service: Could not login user", e);
            throw new ServiceException("Service.CANNOT_LOGIN_USER");
        }
    }

    @Override
    public boolean verifyOtp(HttpServletRequest request, String token){
        try{
            Integer userId = sessionManager.getLoggedUserId(request);
            UserDTO userDTO = userDAO.findUser(userId);
            int authyId = userDTO.getAuthyUserId();
            return authyService.verifyAuthyOTP(token, authyId);
        }
        catch(Exception e){
            LOGGER.error("Service: Could not verify authy OTP", e);
            throw new ServiceException("Service.CANNOT_LOGIN_USER");
        }
    }

    @Override
    public UserDTO findUser(Integer userId) throws ServiceException {
        LOGGER.info("Attempting to find user {}", userId);
        UserDTO userDto = userDAO.findUser(userId);
        if (userDto == null) {
            LOGGER.error("Service: Error finding user {}", userId);
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
                LOGGER.error("Service :Could not create audit event for user update for {}", userId);
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
