package com.itavery.forecast.interaction;

import com.averygrimes.nexus.util.session.SessionManager;
import com.itavery.forecast.request.ProductDemandRequest;
import com.itavery.forecast.service.demand.ProductDemandService;
import com.itavery.forecast.utils.ResponseBuilder;
import com.itavery.forecast.utils.validation.ValidProductDemandRequest;
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
@Path("/productDemand")
public class DemandResource {

    private ProductDemandService productDemandService;
    private SessionManager sessionManager;

    @Inject
    public void setProductDemandService(ProductDemandService productDemandService) {
        this.productDemandService = productDemandService;
    }

    @Inject
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @GET
    @Path("/entryDetails/{productDemandId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDemandEntry(@PathParam("productDemandId") Integer productDemandId) {
        return productDemandService.getDemandEntry(productDemandId);
    }

    @GET
    @Path("/entryDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDemandEntries(@Context HttpServletRequest request) {
        int userId = sessionManager.getLoggedUserId(request);
        return userId != 0 ? productDemandService.getUserDemandEntries(userId)
                : ResponseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "Invalid userId");
    }

    @POST
    @Path("/addEntry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDemandEntry(@Context HttpServletRequest request, @ValidProductDemandRequest ProductDemandRequest pdRequest) {
        int userId = sessionManager.getLoggedUserId(request);
        return userId != 0 ? productDemandService.addDemandEntry(pdRequest, userId)
                : ResponseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "Invalid userId");
    }

    @PUT
    @Path("/updateEntries")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDemandEntry(@Context HttpServletRequest request, @ValidProductDemandRequest List<ProductDemandRequest> pdRequest) {
        int userId = sessionManager.getLoggedUserId(request);
        return userId != 0 ? productDemandService.updateDemandEntry(pdRequest, userId)
                : ResponseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "Invalid userId");
    }

    @PUT
    @Path("/deleteEntry/{productDemandId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDemandEntry(@Context HttpServletRequest request, @PathParam("productDemandId") List<Integer> productDemandId) {
        int userId = sessionManager.getLoggedUserId(request);
        return userId != 0 ? productDemandService.removeDemandEntry(productDemandId, userId)
               : ResponseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "Invalid userId");
    }
}
