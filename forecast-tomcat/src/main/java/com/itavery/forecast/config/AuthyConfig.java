package com.itavery.forecast.config;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  2018-12-11            
 |            
 *===========================================================================*/

import com.authy.AuthyApiClient;
import com.itavery.forecast.credentials.SecretsRetrieval;
import com.twilio.http.TwilioRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthyConfig {

    private final SecretsRetrieval secretsRetrieval;

    public AuthyConfig(final SecretsRetrieval secretsRetrieval){
        this.secretsRetrieval = secretsRetrieval;
    }

    @Bean
    AuthyApiClient AuthyApiClientInit(){
        return new AuthyApiClient(secretsRetrieval.getAuthyApiKey());
    }

    @Bean
    TwilioRestClient twilioRestClientInit(){
        return new TwilioRestClient.Builder(secretsRetrieval.getTwilioSid(), secretsRetrieval.getTwilioToken()).build();
    }
}
