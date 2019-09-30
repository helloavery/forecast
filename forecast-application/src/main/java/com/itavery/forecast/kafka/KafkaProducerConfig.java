package com.itavery.forecast.kafka;

import com.itavery.forecast.config.ProgramArguments;
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

    private com.averygrimes.core.kafka.KafkaProducerConfig coreProducerConfig;
    private ProgramArguments programArguments;

    @Inject
    public void setCoreProducerConfig(com.averygrimes.core.kafka.KafkaProducerConfig coreProducerConfig) {
        this.coreProducerConfig = coreProducerConfig;
    }

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return coreProducerConfig.buildKafkaTemplate(programArguments.getKafkaBootstrapAddress());
    }
}
