package com.itavery.forecast.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  5/15/18
 |
 *===========================================================================*/

import java.io.Serializable;
import java.util.Objects;

public class AccountStatus implements Serializable {

    private Integer accountStatusId;
    private Character status;
    private Character emailVerified;
    private Integer userId;
    private int activeAndVerified;

    public Integer getAccountStatusId() {
        return accountStatusId;
    }

    public void setAccountStatusId(Integer accountStatusId) {
        this.accountStatusId = accountStatusId;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Character getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Character emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountStatus accountStatus = (AccountStatus) o;
        return Objects.equals(accountStatusId, accountStatus.getAccountStatusId()) &&
                Objects.equals(status, accountStatus.getStatus()) &&
                Objects.equals(emailVerified, accountStatus.getEmailVerified()) &&
                Objects.equals(userId, accountStatus.getUserId()) &&
                Objects.equals(activeAndVerified, accountStatus.getActiveAndVerified());
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountStatusId, status, emailVerified, userId, activeAndVerified);
    }
}
