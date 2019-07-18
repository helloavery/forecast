package com.itavery.forecast.exceptions;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-03-27
 * https://github.com/helloavery
 */

public class DAOException extends WebApplicationException {

    private DAOException(Response response){
        super(response);
    }

    public static DAOException buildResponse(int statusCode, String message){
        Response response = generateResponse(statusCode, message);
        return new DAOException(response);
    }

    private static Response generateResponse(int statusCode, String message){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", statusCode);
        errorResponse.put("message", message);
        return Response.status(statusCode).entity(errorResponse).build();
    }
}
