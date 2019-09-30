package com.itavery.forecast.cache;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/24/19
 * https://github.com/helloavery
 */

public interface Cluster {

    Object getHzInstance();

    int getBackupCount();

    int getMaxIdleSeconds();

    int getTtlSeconds();
}
