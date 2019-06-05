package com.itavery.forecast.verification;

import com.itavery.forecast.service.verification.VerificationService;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
@Path("/v1/verification/verify_email")
public class VerificationResourceV1 {

    @Inject
    private VerificationService verificationService;

    @GET
    @Path("/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyUser(@PathParam("token") String token){
        return verificationService.verifyEmailAddress(token);
    }
}
