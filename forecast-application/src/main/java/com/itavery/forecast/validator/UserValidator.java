package com.itavery.forecast.validator;

import com.itavery.forecast.constants.Constants;
import com.itavery.forecast.exceptions.InvalidInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);

    public void validate(String email, String password, String firstName, String lastName) throws InvalidInputException {
        try {
            if (!isValidEmail(email)) {
                throw InvalidInputException.buildResponse(Constants.USER_INVALID_EMAIL);
            } else if (!isValidPassword(password)) {
                throw InvalidInputException.buildResponse(Constants.USER_INVALID_PASSWORD);
            } else if (!isValidFirstName(firstName)) {
                throw InvalidInputException.buildResponse(Constants.USER_INVALID_FIRST_NAME);
            } else if (!isValidLastName(lastName)) {
                throw InvalidInputException.buildResponse(Constants.USER_INVALUD_LAST_NAME);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    private Boolean isValidEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(emailPattern);
    }

    public Boolean isValidPassword(String password) {
        String regexPatternCaps = "(.*[A-Z].*)";
        String regexPatternLower = "(.*[a-z].*)";
        String regexPatternNum = "(.*[\\d].*)";
        return password.matches(regexPatternCaps) &&  password.matches(regexPatternLower)
        && password.matches(regexPatternNum) && password.length() > 7;
    }

    private Boolean isValidFirstName(String firstName) {
        String regexPatternOne = "[A-Z]{1}[a-z]{1,10}";
        String regexPatternTwo = "[A-Z]{1}[a-z]*-{1}[A-Za-z]*";
        return !firstName.matches(regexPatternOne)
                ? firstName.matches(regexPatternTwo)
                : firstName.matches(regexPatternOne);
    }

    private Boolean isValidLastName(String lastName) {
        String regexPatternOne = "[A-Z]{1}[a-z]+";
        String regexPatternTwo = "[A-Z]{1}[a-z]*-{1}[A-Z]{1}[a-z]+";
        return !lastName.matches(regexPatternOne)
                ? lastName.matches(regexPatternTwo)
                : lastName.matches(regexPatternOne);
    }
}
