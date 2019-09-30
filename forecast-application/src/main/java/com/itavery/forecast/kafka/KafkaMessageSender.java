package com.itavery.forecast.kafka;

import com.averygrimes.core.pojo.EmailNotificationRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-14
 * https://github.com/helloavery
 */

@Service
public class KafkaMessageSender {

    private static final Logger LOGGER = LogManager.getLogger(KafkaMessageSender.class);

    private com.averygrimes.core.kafka.KafkaMessageSender kafkaMessageSender;

    @Inject
    public void setKafkaMessageSender(com.averygrimes.core.kafka.KafkaMessageSender kafkaMessageSender) {
        this.kafkaMessageSender = kafkaMessageSender;
    }

    public void sendEmailNotificationMessage(EmailNotificationRequest emailNotificationRequest){
        kafkaMessageSender.sendMessageToTopic("itavery-email", emailNotificationRequest, LOGGER);
    }
}
