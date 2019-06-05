package com.itavery.forecast.constants;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-18
 * https://github.com/helloavery
 */

public enum Regions {

    AMERICAS("AMERICAS", "Americas"),
    EMEA("EMEA", "Europe, Middle East, and Africa"),
    APJ("APJ", "Asia Pacific"),
    APSG("APSG", "Asia Pacific Singapore");

    private final String code;
    private final String name;

    Regions(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
