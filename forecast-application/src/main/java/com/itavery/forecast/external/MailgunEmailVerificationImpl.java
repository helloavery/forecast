package com.itavery.forecast.external;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itavery.forecast.ForecastConstants;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailgunEmailVerificationImpl implements MailgunEmailVerification {

    private static final Logger LOGGER = LogManager.getLogger(MailgunEmailVerificationImpl.class);

    @Override
    public EmailValidationResponse validateEmail(String email) throws Exception {
        try{
            Gson gson = new GsonBuilder().create();
            HttpResponse<JsonNode> request = Unirest.get(ForecastConstants.MAILGUN_MAILBOX_VERIFICATION_URL)
                    .basicAuth("api", ForecastConstants.MAILGUN_PUBLIC_API_KEY)
                    .queryString("address", email)
                    .asJson();
            EmailValidationResponse emailValidationResponse = gson.fromJson(request.getBody().getObject().toString(), EmailValidationResponse.class);
            if(emailValidationResponse == null){
                LOGGER.error("Error retrieving email validation response for e-mail {}", email);
                throw new RuntimeException("Error retrieving email validation response");
            }
            return emailValidationResponse;

        }
        catch(Exception e){
            LOGGER.error("Issue validating account for e-mail {}", email);
            throw e;
        }
    }
}
