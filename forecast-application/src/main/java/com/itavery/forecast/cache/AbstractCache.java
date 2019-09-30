package com.itavery.forecast.cache;

import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.itavery.forecast.utils.exceptions.CacheException;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;


/**
 * @author Avery Grimes-Farrow
 * Created on: 9/25/19
 * https://github.com/helloavery
 */

public abstract class AbstractCache implements CacheBase {

    protected Cluster cluster;
    protected HazelcastInstance hzInstance;
    protected String name;
    protected IMap<String, CacheObject> cacheMap;
    protected Long refreshMillis = 0L;

    public AbstractCache(String name) {
        this.name = name;
    }

    @Override
    public Object getItemFromCache(String key) {
        if(key == null){
            throw new CacheException("");
        }
        if(cacheMap.containsKey(key)){
            CacheObject cacheObject = cacheMap.get(key);
            if(cacheObject != null){
                return cacheObject.getValueObject();
            }
        }
        return null;
    }

    public void putItemInCache(String key, Object value) {
        cacheMap.putIfAbsent(key, createCacheObject(key, value), cluster.getTtlSeconds(), TimeUnit.SECONDS);
    }

    public void putItemInCache(String key, Object value, Long ttl, TimeUnit timeUnit) {
        cacheMap.putIfAbsent(key, createCacheObject(key, value), ttl, timeUnit);
    }

    @Override
    public void updateItemInCache(String key, Object value) {
        cacheMap.put(key, createCacheObject(key, value), cluster.getTtlSeconds(), TimeUnit.SECONDS);
    }

    protected void updateItemInCache(String key, Object value, Long ttl, TimeUnit timeUnit){
        cacheMap.put(key, createCacheObject(key, value), ttl, timeUnit);
    }

    @Override
    public void removeItemFromCache(String key) {
        if(cacheMap.containsKey(key)){
            cacheMap.remove(key);
        }
    }

    @Override
    public void clearCache() {
        cacheMap.clear();
    }

    private CacheObject createCacheObject(String key, Object value){
        CacheObject cacheObject = new CacheObject();
        cacheObject.setKey(key);
        cacheObject.setValueObject((Serializable) value);
        cacheObject.setCreatedTimestamp(System.currentTimeMillis());
        return cacheObject;
    }

    protected MapConfig getMapConfig(){
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName(name);
        mapConfig.setBackupCount(cluster.getBackupCount());
        mapConfig.setMaxIdleSeconds(cluster.getMaxIdleSeconds());
        mapConfig.setMaxSizeConfig(getMaxSizeConfig());
        mapConfig.setEvictionPolicy(EvictionPolicy.LFU);
        return getMapConfig();
    }

    protected boolean cacheExists(HazelcastInstance hzInstance, String name){
        for(DistributedObject distributedObject : hzInstance.getDistributedObjects()){
            if(name.equals(distributedObject.getName())){
                return true;
            }
        }
        return false;
    }

    private MaxSizeConfig getMaxSizeConfig(){
        MaxSizeConfig maxSizeConfig = new MaxSizeConfig();
        maxSizeConfig.setSize(5000);
        return maxSizeConfig;
    }

}
