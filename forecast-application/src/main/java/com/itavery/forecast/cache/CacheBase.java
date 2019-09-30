package com.itavery.forecast.cache;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/24/19
 * https://github.com/helloavery
 */

public interface CacheBase {

    Object getItemFromCache(String key);

    void putItemInCache(String key, Object value);

    void updateItemInCache(String key, Object value);

    void removeItemFromCache(String key);

    void clearCache();
}
