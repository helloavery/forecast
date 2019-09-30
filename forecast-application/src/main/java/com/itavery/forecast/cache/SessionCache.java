package com.itavery.forecast.cache;

import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/25/19
 * https://github.com/helloavery
 */

public interface SessionCache extends CacheBase{

    String SESSION_CACHE_NAME = "sessionMap";
    Long SESSION_TTL_MILLIS = 30L * 60L * 1000L;

    Map<String, Object> get();

    Map<String, Object> get(String key);

    void put(Object value);

    void put(String key, Object value);

    void extend(String sessionId);

    void clear();

    void clear(String sessionId);
}
