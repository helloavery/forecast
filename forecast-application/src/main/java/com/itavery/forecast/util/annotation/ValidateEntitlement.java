package com.itavery.forecast.util.annotation;

import com.itavery.forecast.constants.RoleValues;
import com.itavery.forecast.exceptions.ValidateEntitlementException;
import com.itavery.forecast.service.entitlement.EntitlementService;
import com.itavery.forecast.session.SessionManager;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-24
 * https://github.com/helloavery
 */

@Priority(Priorities.AUTHORIZATION)
public class ValidateEntitlement implements ContainerRequestFilter {

    private SessionManager sessionManager;
    private EntitlementService entitlementService;

    @Inject
    public ValidateEntitlement(SessionManager sessionManager, EntitlementService entitlementService){
        this.sessionManager = sessionManager;
        this.entitlementService = entitlementService;
    }

    @Context
    HttpServletRequest request;
    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        EntitlementPolicy entitlementPolicy = resourceInfo.getResourceMethod().getAnnotation(EntitlementPolicy.class);
        if(entitlementPolicy == null){
            throw ValidateEntitlementException.buildResponse("Role to validate is missing");
        }
        RoleValues roleValue = entitlementPolicy.role();
        checkEntitlement(roleValue);
    }

    private void checkEntitlement(RoleValues roleValue){
        int userId = sessionManager.getLoggedUserId(request);
        entitlementService.matchEntitlementAgainstUser(userId, roleValue);
    }
}
