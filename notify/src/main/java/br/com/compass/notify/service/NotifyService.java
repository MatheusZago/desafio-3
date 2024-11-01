package br.com.compass.notify.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {
    private static final Logger logger = LoggerFactory.getLogger(NotifyService.class);

    @KafkaListener(topics = "notify_topic", groupId = "notify-group")
    public void listen(String message) {

        logger.info("Received message: {}", message);    }
}