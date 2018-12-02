package com.itavery.forecast.regions;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  2/10/18
 |
 *===========================================================================*/

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
