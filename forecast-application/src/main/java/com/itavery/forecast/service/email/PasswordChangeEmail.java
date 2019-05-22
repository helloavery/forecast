package com.itavery.forecast.service.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-19
 * https://github.com/helloavery
 */

public class PasswordChangeEmail extends EmailBase implements EmailContent{

    private static final Logger LOGGER = LogManager.getLogger(PasswordChangeEmail.class);

    private final VelocityEngine velocityEngine;
    private final String name;
    private final String emailAddress;

    public PasswordChangeEmail(final VelocityEngine velocityEngine, final String name, final String emailAddress){
        this.velocityEngine = velocityEngine;
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public String getEmailContent(){
        Template template = velocityEngine.getTemplate("/PasswordChangeEmailTemplate.vm");
        VelocityContext context = new VelocityContext();
        context.put("name", name);
        context.put("dateActioned", "");
        context.put("email", emailAddress);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}
