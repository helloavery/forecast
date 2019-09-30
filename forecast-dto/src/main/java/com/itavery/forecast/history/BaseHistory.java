package com.itavery.forecast.history;

import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/28/19
 * https://github.com/helloavery
 */

public class BaseHistory<T> {

    private Map<String, T> changes;

    public Map<String, T> getChanges() {
        return changes;
    }

    public void setChanges(Map<String, T> changes) {
        this.changes = changes;
    }
}
