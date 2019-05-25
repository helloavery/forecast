package com.itavery.forecast.service.verification;

import com.itavery.forecast.ResponseBuilder;
import com.itavery.forecast.dao.verification.VerificationDAO;
import com.itavery.forecast.enums.AuditType;
import com.itavery.forecast.external.MailgunEmailVerification;
import com.itavery.forecast.service.audit.AuditService;
import com.mashape.unirest.http.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-22
 * https://github.com/helloavery
 */

@Service
public class VerificationServiceImpl implements VerificationService{

    private static final Logger LOGGER = LogManager.getLogger(VerificationServiceImpl.class);
    @Inject
    private VerificationDAO verificationDAO;
    @Inject
    private AuditService auditService;
    @Inject
    private MailgunEmailVerification mailgunEmailVerification;
    @Inject
    ResponseBuilder responseBuilder;

    public String generateToken(String email){
        try{
            SecureRandom secureRandom = new SecureRandom();
            byte bytes[] = new byte[20];
            secureRandom.nextBytes(bytes);
            final int len  = 49;
            char[] allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890="
                    .toCharArray();
            StringBuilder out = new StringBuilder(len);
            for (int i = 0; i < len; i++)
            {
                out.append(allowedChars[secureRandom.nextInt(allowedChars.length)]);
            }
            String token = out.toString();
            verificationDAO.storeToken(token, email);
            return token;
        }
        catch(Exception e){
            LOGGER.error("Error generating token for email {}", email);
            throw new RuntimeException("Error generating token for email " + email);
        }
    }


    public Response verifyEmailAddress(String token){
        try{
            String email = verificationDAO.retrieveEmail(token);
            if(email == null){
                LOGGER.error("Email could not be found for token {}", token);
                return responseBuilder.createFailureResponse(Response.Status.BAD_REQUEST,  "");
            }
            JsonNode emailValidationResponse = mailgunEmailVerification.validateEmail(email);
            boolean isDisposableAddress = (boolean) emailValidationResponse.getObject().get("is_disposable_address");
            boolean isValid = (boolean) emailValidationResponse.getObject().get("is_valid");
            if(!isDisposableAddress && isValid){
                String result = verificationDAO.updateAccountStatus(email);
                auditService.createAudit(email, AuditType.EMAIL_VERIFIED, null);
                return responseBuilder.createSuccessResponse(result);

            }
            else{
                LOGGER.info("E-mail address {} is not valid", email);
                return responseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "E-mail address is not valid");
            }
        }
        catch(Exception e){
            LOGGER.error("Error verifying email address for token {}", token);
            return responseBuilder.createFailureResponse(Response.Status.INTERNAL_SERVER_ERROR, "Error verifying email address for token " + token);
        }
    }
}
