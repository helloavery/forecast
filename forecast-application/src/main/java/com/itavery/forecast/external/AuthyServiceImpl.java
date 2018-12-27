package com.itavery.forecast.external;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  2018-12-10            
 |            
 *===========================================================================*/

import com.authy.AuthyApiClient;
import com.authy.AuthyException;
import com.authy.api.Token;
import com.authy.api.User;
import com.itavery.forecast.AuthyOtpMethod;
import com.itavery.forecast.SessionManager;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.UserDTO;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Service
public class AuthyServiceImpl implements AuthyService{

    private static final Logger LOGGER = LogManager.getLogger(AuthyServiceImpl.class);

    private SessionManager sessionManager;
    private final AuthyApiClient authyClient;
    private final TwilioRestClient twilioRestClient;
    private final RestTemplate restTemplate;

    private static final String AUTHY_SEND_SMS_OTP = "https://api.authy.com/protected/json/sms/%s";
    private static final String AUTHY_VOICE_OTP="https://api.authy.com/protected/json/call/%s";
    private static final String AUTHY_VERIFY_OTP = "https://api.authy.com/protected/json/verify/%s/%s";

    @Inject
    public AuthyServiceImpl(SessionManager sessionManager, final AuthyApiClient authyClient, final TwilioRestClient twilioRestClient, final RestTemplate restTemplate){
        this.sessionManager = sessionManager;
        this.authyClient = authyClient;
        this.twilioRestClient = twilioRestClient;
        this.restTemplate = restTemplate;
    }

    public Integer createAuthyUser(RegistrationDTO user) throws AuthyException {
        try{
            User authyUser = authyClient.getUsers().createUser(user.getEmail(),user.getPhoneNumber(),user.getCountryCode());
            if(authyUser.isOk()){
                int authyUserId = authyUser.getId();
                authyClient.getUsers().requestSms(authyUserId);
                return authyUserId;
            }
            else{
                LOGGER.error("Could not create authy user using e-mail {} and phone number {}-{}", user.getEmail(), user.getCountryCode(), user.getPhoneNumber());
                throw new RuntimeException("Could not create authy user");
            }
        }
        catch(AuthyException e){
            LOGGER.error("Error creating authy user for user {}", user.getEmail(), e.getMessage());
            throw new RuntimeException("Error creating authy user for user: " + user.getEmail(), e);
        }
    }

    public boolean verifyAuthyUser(HttpServletRequest request, String code, int authyUserId) throws AuthyException{
        try{
            Token token = authyClient.getTokens().verify(authyUserId, code);
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
            int userId = userDTO.getUserId();
            int authyId = userDTO.getAuthyUserId();
            String authyOtpRequestURL;
            switch(authyOtpMethod){
                case SMS:
                    authyOtpRequestURL = String.format(AUTHY_SEND_SMS_OTP, authyId);
                    break;
                case VOICE:
                    authyOtpRequestURL = String.format(AUTHY_VOICE_OTP, authyId);
                    break;
                default:
                    authyOtpRequestURL = String.format(AUTHY_SEND_SMS_OTP, authyId);
                    break;
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Authy-API-Key","");
            HttpEntity<?> authyRequest = new HttpEntity<>(headers);
            ResponseEntity<AuthyResponse> authyResponse = restTemplate.exchange(authyOtpRequestURL, HttpMethod.GET, authyRequest, AuthyResponse.class);
            if(authyResponse == null || authyResponse.getBody() == null){
                LOGGER.error("Error retrieving response for Authy OTP via method {} for userId {}", authyOtpMethod, authyId);
                throw new RuntimeException("Error retrieving response for Authy OTP");
            }
            sessionManager.partialLogIn(request, userId);
            return authyResponse.getBody().otpSuccessfullySent();
        }
        catch(Exception e){
            LOGGER.error("Error sending Authy OTP via {} for Authy userId {}", authyOtpMethod, userDTO.getAuthyUserId(), e.getMessage());
            throw new RuntimeException("Error sending", e);
        }
    }

    public boolean verifyAuthyOTP(String token, int authyId){
        try{
            String authyOtpVerifyURL = String.format(AUTHY_VERIFY_OTP, token, authyId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Authy-API-Key","");
            HttpEntity<?> request = new HttpEntity<>(headers);
            ResponseEntity<AuthyResponse> authyResponse = restTemplate.exchange(authyOtpVerifyURL, HttpMethod.GET, request, AuthyResponse.class);
            if(authyResponse == null || authyResponse.getBody() == null){
                LOGGER.error("Error with response for verifying Authy OTP for userId {}", authyId);
                throw new RuntimeException("Error with response for verifying Authy OTP");
            }
            return authyResponse.getBody().otpSuccessfullySent();
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
}
