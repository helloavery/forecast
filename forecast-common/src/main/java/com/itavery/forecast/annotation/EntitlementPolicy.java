package com.itavery.forecast.annotation;

import com.itavery.forecast.enums.RoleValues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-24
 * https://github.com/helloavery
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EntitlementPolicy {

    RoleValues role() default RoleValues.USER;
}
