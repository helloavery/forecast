package com.itavery.forecast.interaction.client;

import com.itavery.forecast.response.AuthyResponse;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-07-17
 * https://github.com/helloavery
 */

@Path("/protected/json")
@Named
public interface AuthyClient {

    @GET
    @Path("/sms/{authyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    AuthyResponse sendSMSOTP(@HeaderParam("X-Authy-API-Key") String apiKey, @PathParam("authyId") int authyId);

    @GET
    @Path("/call/{authyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    AuthyResponse sendVoiceOTP(@HeaderParam("X-Authy-API-Key") String apiKey, @PathParam("authyId") int authyId);

    @GET
    @Path("/verify/{token}/{authyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    AuthyResponse verifyOTP(@HeaderParam("X-Authy-API-Key") String apiKey, @PathParam("token") String token, @PathParam("authyId") int authyId);

}
