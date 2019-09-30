package com.itavery.forecast.utils.exceptions;

import org.apache.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-25
 * https://github.com/helloavery
 */

public class ValidateEntitlementException extends WebApplicationException {

    private ValidateEntitlementException(Response response){
        super(response);
    }

    public static ValidateEntitlementException buildResponse(String message){
        Response response = generateResponse(message);
        return new ValidateEntitlementException(response);
    }

    private static Response generateResponse(String message){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.SC_BAD_REQUEST);
        errorResponse.put("message", message);
        return Response.status(HttpStatus.SC_BAD_REQUEST).entity(errorResponse).build();
    }
}
