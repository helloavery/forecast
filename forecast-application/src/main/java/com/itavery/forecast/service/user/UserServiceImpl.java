package com.itavery.forecast.service.user;

import com.averygrimes.nexus.pojo.EmailNotificationRequest;
import com.averygrimes.nexus.util.session.SessionManager;
import com.itavery.forecast.Constants;
import com.itavery.forecast.dao.user.UserDAO;
import com.itavery.forecast.external.AuthyService;
import com.itavery.forecast.functional.AuditType;
import com.itavery.forecast.kafka.KafkaMessageSender;
import com.itavery.forecast.request.LoginRequest;
import com.itavery.forecast.request.RegistrationRequest;
import com.itavery.forecast.response.UserResponse;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.service.verification.VerificationService;
import com.itavery.forecast.user.User;
import com.itavery.forecast.utils.ResponseBuilder;
import com.itavery.forecast.utils.UUIDUtils;
import com.itavery.forecast.utils.exceptions.ServiceException;
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
    private VerificationService verificationService;
    private AuthyService authyService;
    private KafkaMessageSender kafkaMessageSender;

    @Inject
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Inject
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setVerificationService(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @Inject
    public void setAuthyService(AuthyService authyService) {
        this.authyService = authyService;
    }

    @Inject
    public void setKafkaMessageSender(KafkaMessageSender kafkaMessageSender) {
        this.kafkaMessageSender = kafkaMessageSender;
    }

    @Override
    public Response createUser(HttpServletRequest request, RegistrationRequest regRequest) throws ServiceException {
        try {
            LOGGER.info("Attempting to create user");
            Integer authyId = authyService.createAuthyUser(regRequest.getEmail(), regRequest.getCountryCode(), regRequest.getPhoneNumber());
            if(authyId == null){
                LOGGER.error("Could not create authy user using e-mail {} and phone number {}-{}", regRequest.getEmail(), regRequest.getCountryCode(), regRequest.getPhoneNumber());
                return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, Constants.SERVICE_AUTHY_USER_NOT_CREATED);
            }
            int userId = userDAO.createUser(regRequest, authyId);
            String emailToken = verificationService.generateToken(regRequest.getEmail());
            URI contextUrl = URI.create(request.getRequestURL().toString()).resolve(request.getContextPath());
            EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest();
            emailNotificationRequest.setContextURL(contextUrl.toString());
            emailNotificationRequest.setRecipientEmailAddress(regRequest.getEmail());
            emailNotificationRequest.setRecipientName(regRequest.getFirstName());
            emailNotificationRequest.setEmailToken(emailToken);
            kafkaMessageSender.sendEmailNotificationMessage(emailNotificationRequest);
            auditService.createAudit(regRequest.getUsername(), AuditType.ACCOUNT_CREATED, null);
            auditService.createAudit(regRequest.getUsername(), AuditType.ACCOUNT_ACTIVATED, null);
            partialLogIn(request, userId);
            LOGGER.info("Successfully completed addition of new user {} : {}", regRequest.getUsername(),regRequest.getEmail());
            return ResponseBuilder.createSuccessResponse(userId);
        } catch (Exception e) {
            LOGGER.error("Service: Could not register user", e);
            return ResponseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR,Constants.SERVICE_CANNOT_REGISTER_USER);
        }
    }

    @Override
    public Response verifyAuthyUser(HttpServletRequest request, String code){
        try{
            int userId = sessionManager.getLoggedUserId(request);
            UserResponse userDTO = userDAO.findUser(userId);
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
    public Response login(HttpServletRequest request, LoginRequest loginRequest){
        try{
            UserResponse userDTO = userDAO.findUser(loginRequest.getEmail());
            boolean success = authyService.requestAuthyOTP(request, userDTO, loginRequest.getAuthyOTPMethod());
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
            UserResponse userDTO = userDAO.findUser(userId);
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
    public Response findUser(int userId) throws ServiceException {
        LOGGER.info("Attempting to find user {}", userId);
        UserResponse userDto = userDAO.findUser(userId);
        if (userDto == null) {
            LOGGER.error("Service: Error finding user {}", userId);
            return ResponseBuilder.createFailureResponse(Response.Status.NOT_FOUND, Constants.SERVICE_USER_NOT_FOUND);
        }
        return ResponseBuilder.createSuccessResponse(userDto);
    }

    @Override
    public Response updateUser(User user, int userId) throws ServiceException {
        try {
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
    public Response deactivateUser(int userId) throws ServiceException {
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

    private void fullLogIn(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        session.setAttribute(Constants.AUTHENTICATED, true);
        session.setAttribute(Constants.SESSION_ID, UUIDUtils.generateType4UUID());
    }
}
