package com.itavery.forecast.user;


/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  6/30/18
 |
 *===========================================================================*/

public enum VerificationStatus {

    VERIFIED('A'),
    NOT_VERIFIED('X');

    private final Character code;

    VerificationStatus(final Character code) {
        this.code = code;
    }

    public Character getCode() {
        return code;
    }
}
