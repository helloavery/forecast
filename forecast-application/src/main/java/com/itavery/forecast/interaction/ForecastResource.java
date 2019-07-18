package com.itavery.forecast.interaction;

import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;
import com.itavery.forecast.service.forecast.ProductForecastService;
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
@Path("/productForecast")
public class ForecastResource {

    private ProductForecastService productForecastService;
    private SessionManager sessionManager;

    @Inject
    public ForecastResource(ProductForecastService productForecastService){
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
        Integer userId = sessionManager.getLoggedUserId(request);
        return productForecastService.getForecastEntries(userId);
    }

    @POST
    @Path("/addEntry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addForecastEntry(@Context HttpServletRequest request, ProductForecastDTO productForecastDTO) {
        Integer userId = sessionManager.getLoggedUserId(request);
        return productForecastService.addForecastEntry(productForecastDTO, userId);
    }

    @PUT
    @Path("/updateEntry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateForecastEntry(@Context HttpServletRequest request, List<ProductForecast> productForecastList) {
        Integer userId = sessionManager.getLoggedUserId(request);
        return productForecastService.updateForecastEntries(productForecastList, userId);
    }

    @PUT
    @Path("/deleteEntry/{productForecastId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteForecastEntry(@Context HttpServletRequest request, @PathParam("productForecastId") List<Integer> productForecastId) {
        Integer userId = sessionManager.getLoggedUserId(request);
        return productForecastService.deleteForecastEntry(productForecastId, userId);
    }
}
