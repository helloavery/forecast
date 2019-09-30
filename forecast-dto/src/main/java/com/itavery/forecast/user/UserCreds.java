package com.itavery.forecast.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/29/19
 * https://github.com/helloavery
 */

public class UserCreds implements Serializable {

    private int userId;
    private String password;
    private Timestamp createdDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserCreds userCreds = (UserCreds) o;

        return new EqualsBuilder()
                .append(userId, userCreds.userId)
                .append(password, userCreds.password)
                .append(createdDate, userCreds.createdDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(password)
                .append(createdDate)
                .toHashCode();
    }
}
