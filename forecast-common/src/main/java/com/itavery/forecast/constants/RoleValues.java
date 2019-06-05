package com.itavery.forecast.constants;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-03-10
 * https://github.com/helloavery
 */

public enum RoleValues {

    ADMIN('A', "ADMIN"),
    USER('U', "USER");

    private final Character userRoleValue;
    private final String name;

    RoleValues(final Character userRoleValue, final String name) {
        this.userRoleValue = userRoleValue;
        this.name = name;
    }

    public Character getUserRoleValue() {
        return userRoleValue;
    }

    public String getName() {
        return name;
    }

}
