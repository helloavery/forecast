package com.itavery.forecast.cache;

import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.itavery.forecast.utils.exceptions.CacheException;
import com.itavery.forecast.utils.session.SessionManager;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/25/19
 * https://github.com/helloavery
 */

@Component
public class SessionCacheImpl extends AbstractCache implements SessionCache {

    private SessionManager sessionManager;

    @Inject
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public SessionCacheImpl() {
        super(SESSION_CACHE_NAME);
        SessionCacheHolder.addSessionCache(this);
    }

    @PostConstruct
    public void init(){
        this.hzInstance = (HazelcastInstance) this.cluster.getHzInstance();
        if(cacheExists(hzInstance, name)){
            this.cacheMap = hzInstance.getMap(name);
        }
        else{
            MapConfig mapConfig = getMapConfig();
            mapConfig.setTimeToLiveSeconds(cluster.getTtlSeconds());
            this.hzInstance.getConfig().addMapConfig(mapConfig);
            this.cacheMap = hzInstance.getMap(name);
        }
    }

    @Override
    public Map<String, Object> get() {
        String sessionId = sessionManager.getSessionId();
        if(sessionId == null){
            throw new CacheException("");
        }
        return get(sessionId);
    }

    @Override
    public Map<String, Object> get(String key) {
        Map<String, Object> sessionMap = (Map<String, Object>) super.getItemFromCache(key);
        return sessionMap != null ? sessionMap : new HashMap<>();
    }

    @Override
    public void put(Object value) {
        String sessionId = sessionManager.getSessionId();
        if(sessionId == null){
            throw new CacheException("");
        }
        this.put(sessionId, value);
    }

    @Override
    public void put(String key, Object value) {
        super.putItemInCache(key, value, SESSION_TTL_MILLIS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void extend(String sessionId) {
        if(cacheMap.containsKey(sessionId)){
            CacheObject cacheObject = cacheMap.get(sessionId);
            if((refreshMillis > 0) && System.currentTimeMillis() - cacheObject.getCreatedTimestamp() > refreshMillis){

            }
            super.updateItemInCache(sessionId, cacheObject, SESSION_TTL_MILLIS, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void clear() {
        Map<String, Object> sessionMap = this.get();
        if(MapUtils.isNotEmpty(sessionMap)){
            sessionMap.clear();
        }
    }

    @Override
    public void clear(String sessionId) {
        Map<String, Object> sessionMap = this.get(sessionId);
        if(MapUtils.isNotEmpty(sessionMap)){
            sessionMap.clear();
        }
    }
}
