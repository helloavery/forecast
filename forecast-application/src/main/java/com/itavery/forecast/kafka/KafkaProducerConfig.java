package com.itavery.forecast.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 8/26/19
 * https://github.com/helloavery
 */

@Configuration
public class KafkaProducerConfig {

    private com.averygrimes.nexus.kafka.KafkaProducerConfig nexusProducerConfig;

    @Inject
    public void setCoreProducerConfig(com.averygrimes.nexus.kafka.KafkaProducerConfig nexusProducerConfig) {
        this.nexusProducerConfig = nexusProducerConfig;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return nexusProducerConfig.buildKafkaTemplate();
    }
}
