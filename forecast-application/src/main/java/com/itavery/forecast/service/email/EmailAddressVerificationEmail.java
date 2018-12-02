package com.itavery.forecast.service.email;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/19/18            
 |            
 *===========================================================================*/

import com.itavery.forecast.ForecastConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

public class EmailAddressVerificationEmail extends EmailBase implements EmailContent{

    private static final Logger LOGGER = LogManager.getLogger(EmailAddressVerificationEmail.class);

    private final VelocityEngine velocityEngine;
    private final String name;
    private final String email;
    private final String emailToken;
    private final String environment;

    public EmailAddressVerificationEmail(final VelocityEngine velocityEngine, final String name, final String email, final String emailToken, final String environment){
        this.velocityEngine = velocityEngine;
        this.name = name;
        this.email = email;
        this.emailToken = emailToken;
        this.environment = environment;
    }

    @Override
    public String getEmailContent(){
        Template template = velocityEngine.getTemplate("VerificationEmailTemplate.vm");
        VelocityContext context = new VelocityContext();
        context.put("name", name);
        context.put("email", email);
        context.put("VERIFY_EMAIL_LINK", String.format(getForecasterBaseUrl(environment) + ForecastConstants.VERIFY_EMAIL_ADDRESS_API, emailToken));
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}
