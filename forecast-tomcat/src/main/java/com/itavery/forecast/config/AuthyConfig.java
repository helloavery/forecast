package com.itavery.forecast.config;

import com.authy.AuthyApiClient;
import com.itavery.forecast.credentials.SecretsRetrieval;
import com.twilio.http.TwilioRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-11
 * https://github.com/helloavery
 */

@Configuration
public class AuthyConfig {

    @Inject
    private SecretsRetrieval secretsRetrieval;

    @Bean
    AuthyApiClient AuthyApiClientInit(){
        return new AuthyApiClient(secretsRetrieval.getAuthyApiKey());
    }

    @Bean
    TwilioRestClient twilioRestClientInit(){
        return new TwilioRestClient.Builder(secretsRetrieval.getTwilioSid(), secretsRetrieval.getTwilioToken()).build();
    }
}
