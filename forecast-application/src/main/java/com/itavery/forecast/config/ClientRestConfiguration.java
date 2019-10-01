package com.itavery.forecast.config;

import com.averygrimes.servicediscovery.feign.FeignClientBean;
import com.itavery.forecast.interaction.client.AuthyClient;
import com.itavery.forecast.interaction.client.MailgunClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/29/19
 * https://github.com/helloavery
 */

@Configuration
public class ClientRestConfiguration {

    private ProgramArguments programArguments;

    private static final String AUTHY_SERVICE = "authyService";
    private static final String MAILGUN_SERVICE  = "mailgunService";

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Bean
    public FeignClientBean<AuthyClient> createAuthyClient(){
        String version =  StringUtils.defaultString(programArguments.getAuthyClientVersion(), "1.0.0");
        String instance = StringUtils.defaultString(programArguments.getInstance(), "QA");
        return new FeignClientBean<>(AuthyClient.class, AUTHY_SERVICE, version, instance);
    }

    @Bean
    public FeignClientBean<MailgunClient> createMailgunClient(){
        String version = StringUtils.defaultString(programArguments.getMailgunClientVersion(), "1.0.0");
        String instance = StringUtils.defaultString(programArguments.getInstance(), "QA");
        return new FeignClientBean<>(MailgunClient.class, MAILGUN_SERVICE, version, instance);
    }


}
