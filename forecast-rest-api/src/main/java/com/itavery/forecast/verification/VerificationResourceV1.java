package com.itavery.forecast.verification;

import com.itavery.forecast.service.verification.VerificationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("v1/verification/verify_email")
public class VerificationResourceV1 {

    @Inject
    private VerificationService verificationService;

    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = "application/json")
    public Response verifyUser(@PathVariable("token") String token) throws Exception {
        verificationService.verifyEmailAddress(token);
        return Response.ok().build();
    }
}
