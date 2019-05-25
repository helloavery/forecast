package com.itavery.forecast.enums;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-06-30
 * https://github.com/helloavery
 */

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
