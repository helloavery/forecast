package com.itavery.forecast.external;

import com.authy.AuthyApiClient;
import com.authy.AuthyException;
import com.authy.api.Token;
import com.authy.api.User;
import com.itavery.forecast.constants.AuthyOtpMethod;
import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.interaction.client.ClientRestManager;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.UserDTO;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-10
 * https://github.com/helloavery
 */

@Service
public class AuthyServiceImpl implements AuthyService{

    private static final Logger LOGGER = LogManager.getLogger(AuthyServiceImpl.class);
    private AuthyApiClient authyApiClient;
    private TwilioRestClient twilioRestClient;
    private ClientRestManager authyClient;

    @Inject
    public void setAuthyApiClient(AuthyApiClient authyApiClient) {
        this.authyApiClient = authyApiClient;
    }

    @Inject
    public void setTwilioRestClient(TwilioRestClient twilioRestClient) {
        this.twilioRestClient = twilioRestClient;
    }

    @Inject
    public void setAuthyClient(ClientRestManager authyClient) {
        this.authyClient = authyClient;
    }

    public Integer createAuthyUser(RegistrationDTO user) throws AuthyException {
        try{
            User authyUser = authyApiClient.getUsers().createUser(user.getEmail(),user.getPhoneNumber(),user.getCountryCode());
            if(authyUser.isOk()){
                int authyUserId = authyUser.getId();
                authyApiClient.getUsers().requestSms(authyUserId);
                return authyUserId;
            }
            else{
                LOGGER.error("Could not create authy user using e-mail {} and phone number {}-{}", user.getEmail(), user.getCountryCode(), user.getPhoneNumber());
                throw new RuntimeException("Could not create authy user");
            }
        }
        catch(AuthyException e){
            LOGGER.error("Error creating authy user for user {}", user.getEmail() + e.getMessage());
            throw new RuntimeException("Error creating authy user for user: " + user.getEmail(), e);
        }
    }

    public boolean verifyAuthyUser(HttpServletRequest request, String code, int authyUserId) throws AuthyException{
        try{
            Token token = authyApiClient.getTokens().verify(authyUserId, code);
            if(token.isOk()){
                send("","");
                return true;
            }
            return false;
        }
        catch(AuthyException e){
            LOGGER.error("Error verifying new authy user {}", authyUserId);
            throw new RuntimeException("Error verifying new authy user", e);
        }
    }

    public boolean requestAuthyOTP(HttpServletRequest request, UserDTO userDTO, AuthyOtpMethod authyOtpMethod){
        try{
            AuthyResponse authyResponse = null;
            int userId = userDTO.getUserId();
            int authyId = userDTO.getAuthyUserId();
            switch(authyOtpMethod){
                case SMS:
                    authyResponse = authyClient.sendSMSOTP("", authyId);
                    break;
                case VOICE:
                    authyResponse = authyClient.sendVoiceOTP("", authyId);
                    break;
                default:
                    authyResponse = authyClient.sendSMSOTP("", authyId);
                    break;
            }
            partialLogIn(request, userId);
            return authyResponse.otpSuccessfullySent();
        }
        catch(Exception e){
            LOGGER.error("Error sending Authy OTP via {} for Authy userId {}", authyOtpMethod, userDTO.getAuthyUserId() + e.getMessage());
            throw new RuntimeException("Error sending", e);
        }
    }

    public boolean verifyAuthyOTP(String token, int authyId){
        try{
            AuthyResponse authyResponse = authyClient.verifyOTP("", token, authyId);
            return authyResponse.otpSuccessfullySent();
        }
        catch(Exception e){
            LOGGER.error("Error verifying Authy OTP for authyUserId {}", authyId);
            throw new RuntimeException("Error verifying Authy OTP", e);
        }
    }

    private void send(String to, String message) {
        new MessageCreator(
                new PhoneNumber(to),
                new PhoneNumber(""),
                message
        ).create(twilioRestClient);
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
}
