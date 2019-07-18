package com.itavery.forecast.service.user;

import com.itavery.forecast.ResponseBuilder;
import com.itavery.forecast.constants.AuditType;
import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.dao.user.UserDAO;
import com.itavery.forecast.exceptions.ServiceException;
import com.itavery.forecast.external.AuthyService;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.service.email.EmailService;
import com.itavery.forecast.service.verification.VerificationService;
import com.itavery.forecast.session.SessionManager;
import com.itavery.forecast.user.LoginDTO;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserDTO;
import com.itavery.forecast.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-30
 * https://github.com/helloavery
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private SessionManager sessionManager;
    private AuditService auditService;
    private UserDAO userDAO;
    private UserValidator userValidator;
    private EmailService emailService;
    private VerificationService verificationService;
    private AuthyService authyService;

    @Inject
    public UserServiceImpl(UserDAO userDAO, EmailService emailService, VerificationService verificationService, AuthyService authyService){
        this.userDAO = userDAO;
        this.emailService = emailService;
        this.verificationService = verificationService;
        this.authyService = authyService;
    }

    @Inject
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Inject
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Inject
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @Override
    public Response createUser(HttpServletRequest request, RegistrationDTO registrationDTO) throws ServiceException {
        try {
            LOGGER.info("Validating registration inputs");
            userValidator.validate(registrationDTO.getEmail(), registrationDTO.getPassword(), registrationDTO.getFirstName(), registrationDTO.getLastName());
            LOGGER.info("Attempting to create user");
            Integer authyId = authyService.createAuthyUser(registrationDTO);
            if(authyId == null){
                LOGGER.error("Could not create authy user using e-mail {} and phone number {}-{}", registrationDTO.getEmail(),registrationDTO.getCountryCode(),registrationDTO.getPhoneNumber());
                return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_AUTHY_USER_NOT_CREATED);
            }
            UserDTO userDTO = userDAO.createUser(registrationDTO, authyId);
            if (userDTO == null) {
                LOGGER.error("Error creating user");
                return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_USER_NOT_CREATED);
            }
            String emailToken = verificationService.generateToken(registrationDTO.getEmail());
            URI contextUrl = URI.create(request.getRequestURL().toString()).resolve(request.getContextPath());
            emailService.sendEmailAddressVerificationEmail(contextUrl.toString(), registrationDTO.getEmail(), registrationDTO.getFirstName(), emailToken);
            auditService.createAudit(registrationDTO.getUsername(), AuditType.ACCOUNT_CREATED, null);
            auditService.createAudit(registrationDTO.getUsername(), AuditType.ACCOUNT_ACTIVATED, null);
            partialLogIn(request, userDTO.getUserId());
            LOGGER.info("Successfully completed addition of new user {} : {}", registrationDTO.getUsername(),registrationDTO.getEmail());
            return ResponseBuilder.createSuccessResponse(userDTO);
        } catch (Exception e) {
            LOGGER.error("Service: Could not register user", e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR,Constants.SERVICE_CANNOT_REGISTER_USER);
        }
    }

    @Override
    public Response verifyAuthyUser(HttpServletRequest request, String code){
        try{
            int userId = sessionManager.getLoggedUserId(request);
            UserDTO userDTO = userDAO.findUser(userId);
            int authyUserId = userDTO.getAuthyUserId();
            boolean success =  authyService.verifyAuthyUser(request, code, authyUserId);
            return ResponseBuilder.createSuccessResponse(success);
        }
        catch(Exception e){
            LOGGER.error("Service: Could not verify Authy user", e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_CANNOT_VERIFY_AUTHY_USER);
        }
    }

    @Override
    public Response login(HttpServletRequest request, LoginDTO user){
        try{
            UserDTO userDTO = userDAO.findUser(user.getEmail());
            boolean success = authyService.requestAuthyOTP(request, userDTO, user.getAuthyOtpMethod());
            return ResponseBuilder.createSuccessResponse(success);
        }
        catch(Exception e){
            LOGGER.error("Service: Could not login user", e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_CANNOT_LOGIN_USER);
        }
    }

    @Override
    public Response verifyOtp(HttpServletRequest request, String token){
        try{
            Integer userId = sessionManager.getLoggedUserId(request);
            UserDTO userDTO = userDAO.findUser(userId);
            int authyId = userDTO.getAuthyUserId();
            boolean success =  authyService.verifyAuthyOTP(token, authyId);
            return ResponseBuilder.createSuccessResponse(success);
        }
        catch(Exception e){
            LOGGER.error("Service: Could not verify authy OTP", e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_CANNOT_VERIFY_OTP);
        }
    }

    @Override
    public Response findUser(Integer userId) throws ServiceException {
        LOGGER.info("Attempting to find user {}", userId);
        UserDTO userDto = userDAO.findUser(userId);
        if (userDto == null) {
            LOGGER.error("Service: Error finding user {}", userId);
            return ResponseBuilder.createFailureResponse(Response.Status.NOT_FOUND, Constants.SERVICE_USER_NOT_FOUND);
        }
        return ResponseBuilder.createSuccessResponse(userDto);
    }

    @Override
    public Response updateUser(User user, Integer userId) throws ServiceException {
        try {
            LOGGER.info("Validating updated user information for {}", userId);
            userValidator.validate(user.getEmail(), null, user.getFirstName(), user.getLastName());
            LOGGER.info("Attempting to update user {}", userId);
            userDAO.updateUser(user);
            //TODO: Implement Audit Service
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ACCOUNT_UPDATED, null);
            return ResponseBuilder.createSuccessResponse();
        } catch (Exception e) {
            LOGGER.error("Service: Could not create audit event for user update for {}", userId);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_UPDATING_USER);
        }
    }

    @Override
    public Response deactivateUser(Integer userId) throws ServiceException {
        String returnMessage = null;
        try {
            LOGGER.info("Attempting to deactivate user {}", userId);
            returnMessage = userDAO.deactivateUser(userId);
            auditService.createAudit(Constants.USERID_PREFIX + userId, AuditType.ACCOUNT_DEACTIVATED, null);
            return ResponseBuilder.createSuccessResponse(returnMessage);
        } catch (Exception e) {
            LOGGER.error("Could not deactivate user {}", userId);
            LOGGER.error(e.getMessage(), e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_ERROR_DEACTIVATING_USER);
        }
    }

    private void partialLogIn(HttpServletRequest request, int userId) {
        HttpSession oldSession = request.getSession();
        if(oldSession != null){
            oldSession.invalidate();
        }
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute(Constants.PARTIALLY_AUTHENTICATED, true);
        newSession.setAttribute(Constants.USER_ID, userId);
        newSession.setMaxInactiveInterval(Constants.MAX_INACTIVE_INTERVAL);
        Cookie cookie = new Cookie("message", "welcome");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
    }

    private void fullLogIn(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute(Constants.AUTHENTICATED, true);
    }
}
