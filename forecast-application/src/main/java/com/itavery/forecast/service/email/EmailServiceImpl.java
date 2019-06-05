package com.itavery.forecast.service.email;

import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.constants.AuditType;
import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.credentials.SecretsRetrieval;
import com.itavery.forecast.exceptions.EmailSenderException;
import com.itavery.forecast.service.audit.AuditService;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-07-19
 * https://github.com/helloavery
 */

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LogManager.getLogger(EmailServiceImpl.class);
    @Inject
    private AuditService auditService;
    @Inject
    private VelocityEngine velocityEngine;
    @Inject
    private ProgramArguments programArguments;
    @Inject
    private SecretsRetrieval secretsRetrieval;

    @Override
    public void sendEmailAddressVerificationEmail(String emailAddress, String name, String emailToken) throws Exception {
        String subject = "Please verify your Forecaster E-mail Address";
        EmailContent emailContent = getVerificationEmail(emailAddress, name, emailToken, programArguments.getEnvironment());
        if(sendEmail(emailAddress,subject,emailContent)){
            auditService.createAudit(emailAddress, AuditType.EMAIL_VERIFICATION_CREATED, null);
        }
        else{
            LOGGER.error("Error sending e-mail");
            throw new EmailSenderException("Could not send account e-mail verification for e-mail: " + emailAddress);
        }
    }

    @Override
    public void sendPasswordChangeEmail(String emailAddress, String name) throws Exception{
        String subject = "Forecaster Password Change";
        EmailContent emailContent = getPasswordChangeEmail(emailAddress, name);
        if(!sendEmail(emailAddress,subject,emailContent)){
            LOGGER.error("Error sending e-mail");
            throw new EmailSenderException("Could not send account password change for e-mail: " + emailAddress);
        }
    }

    @Override
    public EmailContent getVerificationEmail(String emailAddress, String name, String emailToken, String environment){
        return new EmailAddressVerificationEmail(velocityEngine, name, emailAddress, emailToken, environment);
    }

    @Override
    public EmailContent getPasswordChangeEmail(String emailAddress, String name){
        return new PasswordChangeEmail(velocityEngine, name, emailAddress);
    }

    private boolean sendEmail(String emailAddress,String subject,EmailContent emailContent) {
        boolean emailEventSuccessful = false;
        try {
            Configuration configuration = new Configuration()
                    .domain(Constants.MAILGUN_DOMAIN_NAME)
                    .apiKey(secretsRetrieval.getMailgunApiKey())
                    .from("Do-Not-Reply", "donotreply@forecaster.itavery.com");
            Mail.using(configuration)
                    .to(emailAddress)
                    .subject(subject)
                    .text("Please take appropriate action")
                    .html(emailContent.getEmailContent())
                    .build()
                    .send();
            LOGGER.info("Successfully sent {} email to {}", subject, emailAddress);
            emailEventSuccessful = true;
        } catch (Exception e) {
            LOGGER.error("Error sending e-mail {} to {}", subject, emailAddress);
        }
        return emailEventSuccessful;
    }
}
