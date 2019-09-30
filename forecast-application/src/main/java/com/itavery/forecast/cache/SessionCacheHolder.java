package com.itavery.forecast.cache;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/25/19
 * https://github.com/helloavery
 */

public class SessionCacheHolder {

    private static List<SessionCache> sessionCaches = new ArrayList<>();

    private SessionCacheHolder() {
    }

    public static void addSessionCache(SessionCache sessionCache){
        sessionCaches.add(sessionCache);
    }

    public static List<SessionCache> getAllSessionCaches(){
        return Collections.unmodifiableList(sessionCaches);
    }
}
