package com.itavery.forecast.external;

import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.credentials.SecretsRetrieval;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-01
 * https://github.com/helloavery
 */

@Service
public class MailgunEmailVerificationImpl implements MailgunEmailVerification {

    private static final Logger LOGGER = LogManager.getLogger(MailgunEmailVerificationImpl.class);
    @Inject
    private  SecretsRetrieval secretsRetrieval;

    @Override
    public JsonNode validateEmail(String email) throws Exception {
        try{
            HttpResponse<JsonNode> request = Unirest.get(Constants.MAILGUN_MAILBOX_VERIFICATION_URL)
                    .basicAuth("api", secretsRetrieval.getMailgunApiKey())
                    .queryString("address", email)
                    .asJson();
            if(request == null){
                LOGGER.error("Error retrieving email validation response for e-mail {}", email);
                throw new RuntimeException("Error retrieving email validation response");
            }
            return request.getBody();
        }
        catch(Exception e){
            LOGGER.error("Issue validating account for e-mail {}", email);
            throw e;
        }
    }
}
