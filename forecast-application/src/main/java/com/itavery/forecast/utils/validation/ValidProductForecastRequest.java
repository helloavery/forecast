package com.itavery.forecast.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/27/19
 * https://github.com/helloavery
 */

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductForecastValidator.class)
@Documented
public @interface ValidProductForecastRequest {

    String message () default "Not a valid request. Please check the provided entries.";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};
}
