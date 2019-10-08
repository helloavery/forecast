package com.itavery.forecast.interaction;

import com.averygrimes.nexus.util.session.SessionManager;
import com.itavery.forecast.request.ProductForecastRequest;
import com.itavery.forecast.service.forecast.ProductForecastService;
import com.itavery.forecast.utils.ResponseBuilder;
import com.itavery.forecast.utils.validation.ValidProductForecastRequest;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@Path("/productForecast")
public class ForecastResource {

    private ProductForecastService productForecastService;
    private SessionManager sessionManager;

    @Inject
    public void setProductForecastService(ProductForecastService productForecastService) {
        this.productForecastService = productForecastService;
    }

    @Inject
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @GET
    @Path("/entryDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForecastEntries(@Context HttpServletRequest request) {
        int userId = sessionManager.getLoggedUserId(request);
        return productForecastService.getForecastEntries(userId);
    }

    @POST
    @Path("/addEntry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addForecastEntry(@Context HttpServletRequest request, @ValidProductForecastRequest ProductForecastRequest pfRequest) {
        int userId = sessionManager.getLoggedUserId(request);
        return userId != 0 ? productForecastService.addForecastEntry(pfRequest, userId)
                : ResponseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "Invalid userId");
    }

    @PUT
    @Path("/updateEntry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateForecastEntry(@Context HttpServletRequest request, @ValidProductForecastRequest List<ProductForecastRequest> productForecastList) {
        int userId = sessionManager.getLoggedUserId(request);
        return userId != 0 ? productForecastService.updateForecastEntries(productForecastList, userId)
                : ResponseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "Invalid userId");
    }

    @PUT
    @Path("/deleteEntry/{productForecastId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteForecastEntry(@Context HttpServletRequest request, @PathParam("productForecastId") List<Integer> productForecastId) {
        int userId = sessionManager.getLoggedUserId(request);
        return userId != 0 ? productForecastService.deleteForecastEntry(productForecastId, userId)
                : ResponseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "Invalid userId");
    }
}
