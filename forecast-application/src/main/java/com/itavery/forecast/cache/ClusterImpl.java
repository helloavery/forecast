package com.itavery.forecast.cache;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.NativeMemoryConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.memory.MemorySize;
import com.hazelcast.memory.MemoryUnit;

import javax.annotation.PostConstruct;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/24/19
 * https://github.com/helloavery
 */

public class ClusterImpl implements Cluster {

    private HazelcastInstance hzInstance;
    private int backupCount;
    private int maxIdleSeconds;
    private int ttlSeconds;

    @PostConstruct
    public void init(){
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setGroupConfig(new GroupConfig("dev", "dev-pass"));
        clientConfig.getNetworkConfig().addAddress("10.90.0.1", "10.90.0.2:5702");

        MemorySize memorySize = new MemorySize(512, MemoryUnit.MEGABYTES);
        NativeMemoryConfig nativeMemoryConfig =
                new NativeMemoryConfig()
                        .setAllocatorType(NativeMemoryConfig.MemoryAllocatorType.POOLED)
                        .setSize(memorySize)
                        .setEnabled(true)
                        .setMinBlockSize(16)
                        .setPageSize(1 << 20);
        clientConfig.setNativeMemoryConfig(nativeMemoryConfig);

        hzInstance = HazelcastClient.newHazelcastClient(clientConfig);

    }

    public Object getHzInstance(){
        return this.hzInstance;
    }

    public int getBackupCount(){
        return this.backupCount;
    }

    public int getMaxIdleSeconds() {
        return maxIdleSeconds;
    }

    public int getTtlSeconds() {
        return ttlSeconds;
    }
}
