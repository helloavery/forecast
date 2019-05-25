package com.itavery.forecast.annotation;

import com.itavery.forecast.enums.RoleValues;
import com.itavery.forecast.exceptions.InvalidEntitlementException;
import com.itavery.forecast.exceptions.ValidateEntitlementException;
import com.itavery.forecast.service.entitlement.EntitlementService;
import com.itavery.forecast.session.SessionManager;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-24
 * https://github.com/helloavery
 */

public class ValidateEntitlement {

    @Inject
    private SessionManager sessionManager;
    @Inject
    private EntitlementService entitlementService;
    @Context
    HttpServletRequest request;

    public void checkUserEntitlement(Object object) throws InvalidEntitlementException, ValidateEntitlementException {
        try {
            initializeObject(object);
            checkEntitlement(object);

        } catch (Exception e) {
            throw new ValidateEntitlementException(e.getMessage());
        }
    }

    private void initializeObject(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(EntitlementPolicy.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private RoleValues getRoleValue(Field field) {
        return field.getAnnotation(EntitlementPolicy.class).role();
    }

    private void checkEntitlement(Object object){
        RoleValues roleValue = null;
        int userId = sessionManager.getLoggedUserId(request);
        Class<?> clazz = object.getClass();
        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            if(field.isAnnotationPresent(EntitlementPolicy.class)){
                roleValue  =  getRoleValue(field);
            }
        }
        if(roleValue == null){
            throw new ValidateEntitlementException("Role to validate is missing");
        }
        entitlementService.matchEntitlementAgainstUser(userId, roleValue);
    }

}
