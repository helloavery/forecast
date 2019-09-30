package com.itavery.forecast.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-05-15
 * https://github.com/helloavery
 */

public class AccountStatus implements Serializable {

    private static final long serialVersionUID = 5136001430949506792L;

    private char status;
    private int emailVerified;
    private int userId;
    private int activeAndVerified;

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(int emailVerified) {
        this.emailVerified = emailVerified;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActiveAndVerified() {
        return activeAndVerified;
    }

    public void setActiveAndVerified(int activeAndVerified) {
        this.activeAndVerified = activeAndVerified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AccountStatus that = (AccountStatus) o;

        return new EqualsBuilder()
                .append(userId, that.userId)
                .append(activeAndVerified, that.activeAndVerified)
                .append(status, that.status)
                .append(emailVerified, that.emailVerified)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(status)
                .append(emailVerified)
                .append(userId)
                .append(activeAndVerified)
                .toHashCode();
    }
}
