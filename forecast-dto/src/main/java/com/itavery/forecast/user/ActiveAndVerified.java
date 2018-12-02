package com.itavery.forecast.user;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  6/30/18
 |
 *===========================================================================*/

public enum ActiveAndVerified {

    TRUE(1),
    FALSE(0);

    private final int value;

    ActiveAndVerified(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
