package com.itavery.forecast.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  5/10/18
 |
 *===========================================================================*/


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Credentials implements Serializable {

    private Integer userCredId;
    private String password;
    private Integer userId;
    private Timestamp createdDate;
    private int timesModified;

    public Integer getUserCredId() {
        return userCredId;
    }

    public void setUserCredId(Integer userCredId) {
        this.userCredId = userCredId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public int getTimesModified() {
        return timesModified;
    }

    public void setTimesModified(int timesModified) {
        this.timesModified = timesModified;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Credentials credentials = (Credentials) o;
        return Objects.equals(userCredId, credentials.getUserCredId()) &&
                Objects.equals(password, credentials.getPassword()) &&
                Objects.equals(userId, credentials.getUserId()) &&
                Objects.equals(createdDate, credentials.getCreatedDate()) &&
                Objects.equals(timesModified, credentials.getTimesModified());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCredId, password, userId, createdDate, timesModified);
    }
}
