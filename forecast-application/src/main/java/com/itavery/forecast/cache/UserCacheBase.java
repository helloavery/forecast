package com.itavery.forecast.cache;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/24/19
 * https://github.com/helloavery
 */

/*public class UserCacheBase extends AbstractCache {

    private Integer ttlSeconds;

    public UserCacheBase(String name) {
        super(name);
        this.ttlSeconds = cluster.getTtlSeconds();
    }

    public UserCacheBase(String name, Integer ttlSeconds) {
        super(name);
        this.ttlSeconds = ttlSeconds;
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

    public void putItemInCache(String key, Object value) {
        super.putItemInCache(key, value, ttlSeconds.longValue(), TimeUnit.SECONDS);
    }

}*/
