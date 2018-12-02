package com.itavery.forecast;

import com.itavery.forecast.product.ProductDemand;
import com.itavery.forecast.product.ProductDemandDTO;
import com.itavery.forecast.service.demand.ProductDemandService;
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
@RequestMapping("/api/productDemand")
public class DemandResource {

    private final ProductDemandService productDemandService;
    private Provider provider;

    public DemandResource(ProductDemandService productDemandService, Provider provider) {
        this.productDemandService = productDemandService;
        this.provider = provider;
    }

    @RequestMapping(value = "/entryDetails/{productDemandId}", method = RequestMethod.GET)
    public Response getDemandEntry(@PathVariable("productDemandId") Integer productDemandId) {
        return Response.ok(Response.Status.OK).entity(productDemandService.getDemandEntry(productDemandId)).build();
    }

    @RequestMapping(value = "/entryDetails", method = RequestMethod.GET)
    public Response getUserDemandEntries(@Context HttpServletRequest request) {
        Integer userId = provider.getUserId(request);
        return Response.ok(Response.Status.OK).entity(productDemandService.getUserDemandEntries(userId)).build();
    }

    @RequestMapping(value = "/addEntry", method = RequestMethod.POST, consumes = "application/json")
    public Response addDemandEntry(@Context HttpServletRequest request, @RequestBody ProductDemandDTO productDemand) {
        Integer userId = provider.getUserId(request);
        return Response.ok(Response.Status.OK).entity(productDemandService.addDemandEntry(productDemand, userId)).build();
    }

    @RequestMapping(value = "/updateEntries", method = RequestMethod.PUT)
    public Response updateDemandEntry(@Context HttpServletRequest request, @RequestBody List<ProductDemand> productDemandList) {
        Integer userId = provider.getUserId(request);
        productDemandService.updateDemandEntry(productDemandList, userId);
        return Response.ok().build();
    }

    @RequestMapping(value = "/deleteEntry/{productDemandId}", method = RequestMethod.PUT)
    public Response deleteDemandEntry(@Context HttpServletRequest request, @PathVariable("productDemandId") List<Integer> productDemandId) {
        Integer userId = provider.getUserId(request);
        return Response.ok(productDemandService.removeDemandEntry(productDemandId, userId)).build();
    }
}
