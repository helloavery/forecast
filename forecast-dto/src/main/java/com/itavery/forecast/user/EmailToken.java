package com.itavery.forecast.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/28/19
 * https://github.com/helloavery
 */

public class EmailToken implements Serializable {

    private static final long serialVersionUID = -7885494756451164746L;

    private String email;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmailToken that = (EmailToken) o;

        return new EqualsBuilder()
                .append(email, that.email)
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(email)
                .append(token)
                .toHashCode();
    }
}
