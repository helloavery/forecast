package com.itavery.forecast.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/27/19
 * https://github.com/helloavery
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest extends BaseUserRequest{

    @NotNull
    @FormParam("firstName")
    private String firstName;

    @NotNull
    @FormParam("lastName")
    private String lastName;

    @NotNull
    @FormParam("username")
    private String username;

    @NotNull
    @FormParam("password")
    private String password;

    @NotNull
    @FormParam("countryCode")
    private String countryCode;

    @NotNull @FormParam("phoneNumber")
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
