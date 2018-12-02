package com.itavery.forecast;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  6/30/18
 |
 *===========================================================================*/

public enum BooleanToIntAdaptor {

    TRUE(1),
    FALSE(0);

    private final int value;

    BooleanToIntAdaptor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
