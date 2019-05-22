package com.itavery.forecast.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-08-12
 * https://github.com/helloavery
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String countryCode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
