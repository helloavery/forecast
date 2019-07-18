package com.itavery.forecast.external;

import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.interaction.client.ClientRestManager;
import com.itavery.forecast.util.credentials.SecretsRetrieval;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.utils.Base64Coder;
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
    private SecretsRetrieval secretsRetrieval;
    private ClientRestManager mailgunClient;

    @Inject
    public MailgunEmailVerificationImpl(SecretsRetrieval secretsRetrieval){
        this.secretsRetrieval = secretsRetrieval;
    }

    @Inject
    public void setMailgunClient(ClientRestManager mailgunClient) {
        this.mailgunClient = mailgunClient;
    }

    @Override
    public JsonNode validateEmail(String email) throws Exception {
        try{
            String headerAuthKey = createBasicAuthHeader("api", secretsRetrieval.getMailgunApiKey());
            HttpResponse<JsonNode> request1 = mailgunClient.verifyEmailAddress(headerAuthKey, email);
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

    private String createBasicAuthHeader(String username, String password){
        return "Basic " + Base64Coder.encodeString(username + ":" + password);
    }
}
