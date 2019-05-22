package com.itavery.forecast.mithra.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-05
 * https://github.com/helloavery
 */

@Target(value = {METHOD, TYPE})
@Retention(value = RUNTIME)
@Documented
@Inherited
public @interface Transactional {
}
