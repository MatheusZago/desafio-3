package br.com.compass.notify.service;

import br.com.compass.notify.model.NotifyMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {

    @KafkaListener(topics = "notify_topic", groupId = "notify-group")
    public void listen(String message) {
        // Processar a mensagem JSON como String
        System.out.println("Received message: " + message);
    }
}