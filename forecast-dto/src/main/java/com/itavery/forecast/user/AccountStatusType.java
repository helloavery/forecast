package com.itavery.forecast.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  6/30/18
 |
 *===========================================================================*/

public enum AccountStatusType {

    ACTIVE('A', "Active"),
    SUSPENDED('S', "Suspended"),
    DEACTIVATED('X', "Deactivated");

    private final Character code;
    private final String name;

    AccountStatusType(final Character code, final String name) {
        this.code = code;
        this.name = name;
    }

    public Character getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
