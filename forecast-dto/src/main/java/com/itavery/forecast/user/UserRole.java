package com.itavery.forecast.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-06-30
 * https://github.com/helloavery
 */

public class UserRole implements Serializable {

    private Integer userId;
    private Integer roleId;

    public UserRole(Integer userId, Integer roleId) {
        super();
        this.userId = userId;
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserRole userRole = (UserRole) o;
        return Objects.equals(userId, userRole.getUserId()) &&
                Objects.equals(roleId, userRole.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
