package com.itavery.forecast;

import com.itavery.forecast.service.verification.VerificationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("v1/verification/verify_email")
public class VerificationResource {

    private final VerificationService verificationService;

    public VerificationResource(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = "application/json")
    public Response verifyUser(@PathVariable("token") String token) throws Exception {
        verificationService.verifyEmailAddress(token);
        return Response.ok().build();
    }
}
