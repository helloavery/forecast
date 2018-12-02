package com.itavery.forecast.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  3/10/18
 |
 *===========================================================================*/

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
