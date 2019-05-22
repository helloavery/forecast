package com.itavery.forecast.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-18
 * https://github.com/helloavery
 */

public class User implements Serializable {

    private Integer userId;
    private Integer authyId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String countryCode;
    private String phoneNumber;
    private String region;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAuthyId() {
        return authyId;
    }

    public void setAuthyId(Integer authyId) {
        this.authyId = authyId;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userId, user.getUserId()) &&
                Objects.equals(firstName, user.getFirstName()) &&
                Objects.equals(lastName, user.getLastName()) &&
                Objects.equals(username, user.getUsername()) &&
                Objects.equals(email, user.getEmail()) &&
                Objects.equals(countryCode, user.getCountryCode()) &&
                Objects.equals(phoneNumber, user.getPhoneNumber()) &&
                Objects.equals(region, user.getRegion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, username, email, countryCode, phoneNumber, region);
    }
}





