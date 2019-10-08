package com.itavery.forecast.config;

import com.averygrimes.servicediscovery.SimpleFeignClientBean;
import com.itavery.forecast.interaction.client.AuthyClient;
import com.itavery.forecast.interaction.client.MailgunClient;
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

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Bean
    public SimpleFeignClientBean<AuthyClient> createAuthyClient(){
        return new SimpleFeignClientBean<>(AuthyClient.class, programArguments.getAuthyClientEndpoint());
    }

    @Bean
    public SimpleFeignClientBean<MailgunClient> createMailgunClient(){
        return new SimpleFeignClientBean<>(MailgunClient.class, programArguments.getMailgunClientEndpoint());
    }


}
