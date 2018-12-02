package com.itavery.forecast;

import com.itavery.forecast.product.ProductForecast;
import com.itavery.forecast.product.ProductForecastDTO;
import com.itavery.forecast.service.forecast.ProductForecastService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/api/productForecast")
public class ForecastResource {

    private ProductForecastService productForecastService;
    private Provider provider;

    public ForecastResource(ProductForecastService productForecastService, Provider provider) {
        this.productForecastService = productForecastService;
        this.provider = provider;
    }

    @RequestMapping(value = "/entryDetails", method = RequestMethod.GET, produces = "application/json")
    public Response getForecastEntries(@Context HttpServletRequest request) {
        Integer userId = provider.getUserId(request);
        return Response.status(Response.Status.OK).entity(productForecastService.getForecastEntries(userId)).build();
    }

    @RequestMapping(value = "/addEntry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response addForecastEntry(@Context HttpServletRequest request, @RequestBody ProductForecastDTO productForecastDTO) {
        Integer userId = provider.getUserId(request);
        return Response.status(Response.Status.OK).entity(productForecastService.addForecastEntry(productForecastDTO, userId)).build();
    }

    @RequestMapping(value = "/updateEntry", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response updateForecastEntry(@Context HttpServletRequest request, @RequestBody List<ProductForecast> productForecastList) {
        Integer userId = provider.getUserId(request);
        return Response.status(Response.Status.OK).entity(productForecastService.updateForecastEntries(productForecastList, userId)).build();
    }

    @RequestMapping(value = "/deleteEntry/{productForecastId}", method = RequestMethod.PUT, produces = "application/json")
    public Response deleteForecastEntry(@Context HttpServletRequest request, @PathVariable("productForecastId") List<Integer> productForecastId) {
        Integer userId = provider.getUserId(request);
        return Response.status(Response.Status.OK).entity(productForecastService.deleteForecastEntry(productForecastId, userId)).build();
    }
}
