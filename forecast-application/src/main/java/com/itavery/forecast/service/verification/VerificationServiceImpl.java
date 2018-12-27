package com.itavery.forecast.service.verification;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/22/18            
 |            
 *===========================================================================*/

import com.itavery.forecast.audit.AuditType;
import com.itavery.forecast.dao.verification.VerificationDAO;
import com.itavery.forecast.external.MailgunEmailVerification;
import com.itavery.forecast.service.audit.AuditService;
import com.mashape.unirest.http.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.security.SecureRandom;

@Service
public class VerificationServiceImpl implements VerificationService{

    private static final Logger LOGGER = LogManager.getLogger(VerificationServiceImpl.class);

    private VerificationDAO verificationDAO;
    private AuditService auditService;
    private final MailgunEmailVerification mailgunEmailVerification;

    @Inject
    public VerificationServiceImpl(VerificationDAO verificationDAO, AuditService auditService, final MailgunEmailVerification mailgunEmailVerification){
        this.verificationDAO = verificationDAO;
        this.auditService = auditService;
        this.mailgunEmailVerification = mailgunEmailVerification;
    }

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
            throw new RuntimeException("Error generating token for email " + email, e);
        }
    }


    public void verifyEmailAddress(String token){
        try{
            String email = verificationDAO.retrieveEmail(token);
            if(email == null){
                LOGGER.error("Email could not be found for token {}", token);
                throw new NullPointerException("");
            }
            JsonNode emailValidationResponse = mailgunEmailVerification.validateEmail(email);
            boolean isDisposableAddress = (boolean) emailValidationResponse.getObject().get("is_disposable_address");
            boolean isValid = (boolean) emailValidationResponse.getObject().get("is_valid");
            if(!isDisposableAddress && isValid){
                verificationDAO.updateAccountStatus(email);
                auditService.createAudit(email, AuditType.EMAIL_VERIFIED, null);
            }
            else{
                LOGGER.info("E-mail address {} is not valid", email);
            }
        }
        catch(Exception e){
            LOGGER.error("Error verifying email address for token {}", token);
            throw new RuntimeException("Error verifying email address for token " + token, e);
        }
    }
}
