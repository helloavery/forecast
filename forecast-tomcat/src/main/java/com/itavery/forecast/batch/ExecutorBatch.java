package com.itavery.forecast.batch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-18
 * https://github.com/helloavery
 */

public abstract class ExecutorBatch<T> {

    ExecutorService executor = Executors.newFixedThreadPool(10);

    protected void execute(){
        init();
        executor.shutdown();
        executor.shutdownNow();
        try {
            executor.awaitTermination(20l, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void init();
}
