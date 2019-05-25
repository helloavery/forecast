package com.itavery.forecast;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-25
 * https://github.com/helloavery
 */

@Component
public class ResponseBuilder {

    public Response createSuccessResponse(){
        return Response.status(Response.Status.OK).build();
    }

    public Response createSuccessResponse(Object entity){
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    public Response createFailureResponse(Response.Status status, String message){
        return Response.status(status).entity(message).build();
    }
}
