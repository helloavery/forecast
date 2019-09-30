package com.itavery.forecast.utils.validation;

import com.itavery.forecast.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class UserValidator implements ConstraintValidator<ValidUserRequest, Object> {

    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);

    @Override
    public void initialize(ValidUserRequest constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object request, ConstraintValidatorContext constraintValidatorContext) {
        try{
            Method emailInstanceMethod =  request.getClass().getMethod("getEmail");
            Method passwordInstanceMethod = request.getClass().getMethod("getPassword");
            Method firstNameInstanceMethod = request.getClass().getMethod("getFirstName");
            Method lastNameInstanceMethod = request.getClass().getMethod("getLastName");
            if (!isValidEmail((String) emailInstanceMethod.invoke(request))) {
                LOGGER.warn(Constants.USER_INVALID_EMAIL);
                return false;
            } else if (!isValidPassword((String) passwordInstanceMethod.invoke(request))) {
                LOGGER.warn(Constants.USER_INVALID_PASSWORD);
                return false;
            } else if (!isValidFirstName((String)firstNameInstanceMethod.invoke(request))) {
                LOGGER.warn(Constants.USER_INVALID_FIRST_NAME);
                return false;
            } else if (!isValidLastName((String) lastNameInstanceMethod.invoke(request))) {
                LOGGER.warn(Constants.USER_INVALID_LAST_NAME);
                return false;
            }
            return true;
        }
        catch(Exception e){
            LOGGER.error("Error calling method for request {} during user input validation", request);
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(emailPattern);
    }

    private boolean isValidPassword(String password) {
        String regexPatternCaps = "(.*[A-Z].*)";
        String regexPatternLower = "(.*[a-z].*)";
        String regexPatternNum = "(.*[\\d].*)";
        return password.matches(regexPatternCaps) &&  password.matches(regexPatternLower)
        && password.matches(regexPatternNum) && password.length() > 7;
    }

    private boolean isValidFirstName(String firstName) {
        String regexPatternOne = "[A-Z]{1}[a-z]{1,10}";
        String regexPatternTwo = "[A-Z]{1}[a-z]*-{1}[A-Za-z]*";
        return !firstName.matches(regexPatternOne)
                ? firstName.matches(regexPatternTwo)
                : firstName.matches(regexPatternOne);
    }

    private boolean isValidLastName(String lastName) {
        String regexPatternOne = "[A-Z]{1}[a-z]+";
        String regexPatternTwo = "[A-Z]{1}[a-z]*-{1}[A-Z]{1}[a-z]+";
        return !lastName.matches(regexPatternOne)
                ? lastName.matches(regexPatternTwo)
                : lastName.matches(regexPatternOne);
    }
}
