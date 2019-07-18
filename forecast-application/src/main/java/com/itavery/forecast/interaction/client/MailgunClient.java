package com.itavery.forecast.interaction.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-07-17
 * https://github.com/helloavery
 */

public interface MailgunClient {

    @GET
    @Path("/address/private/validate")
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<JsonNode> verifyEmailAddress(@HeaderParam("Authorization") String basicAuth, @QueryParam("address") String address);
}

