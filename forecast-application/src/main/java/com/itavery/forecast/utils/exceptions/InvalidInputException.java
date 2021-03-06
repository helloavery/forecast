package com.itavery.forecast.utils.exceptions;

import org.apache.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-03-27
 * https://github.com/helloavery
 */

public class InvalidInputException extends WebApplicationException {

    private InvalidInputException(Response response){
        super(response);
    }

    public static InvalidInputException buildResponse(String message){
        Response response = generateResponse(message);
        return new InvalidInputException(response);
    }

    private static Response generateResponse(String message){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.SC_BAD_REQUEST);
        errorResponse.put("message", message);
        return Response.status(HttpStatus.SC_BAD_REQUEST).entity(errorResponse).build();
    }
}
