package com.itavery.forecast.demand;

import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;
import com.itavery.forecast.service.demand.ProductDemandService;
import com.itavery.forecast.session.SessionManager;
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
@Path("v1/productDemand")
public class DemandResourceV1 {

    @Inject
    private ProductDemandService productDemandService;
    @Inject
    private SessionManager sessionManager;

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
        Integer userId = sessionManager.getLoggedUserId(request);
        return productDemandService.getUserDemandEntries(userId);
    }

    @POST
    @Path("/addEntry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDemandEntry(@Context HttpServletRequest request, ProductDemandDTO productDemand) {
        Integer userId = sessionManager.getLoggedUserId(request);
        return productDemandService.addDemandEntry(productDemand, userId);
    }

    @PUT
    @Path("/updateEntries")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDemandEntry(@Context HttpServletRequest request, List<ProductDemand> productDemandList) {
        Integer userId = sessionManager.getLoggedUserId(request);
        return productDemandService.updateDemandEntry(productDemandList, userId);
    }

    @PUT
    @Path("/deleteEntry/{productDemandId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDemandEntry(@Context HttpServletRequest request, @PathParam("productDemandId") List<Integer> productDemandId) {
        Integer userId = sessionManager.getLoggedUserId(request);
        return productDemandService.removeDemandEntry(productDemandId, userId);
    }
}
