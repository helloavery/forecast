package com.itavery.forecast;


import javax.ws.rs.core.Response;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-25
 * https://github.com/helloavery
 */

public class ResponseBuilder {

    public static Response createSuccessResponse(){
        return Response.status(Response.Status.OK).build();
    }

    public static Response createSuccessResponse(Object entity){
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    public static Response createFailureResponse(Response.Status status, String message){
        return Response.status(status).entity(message).build();
    }
}
