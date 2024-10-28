package br.com.compass.notify.service;

import br.com.compass.notify.model.NotifyMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {

    //Ele ta ouvindo o que no grupo e tópico
    @KafkaListener(topics = "notify_topic", groupId = "notify-group")
    public void listen(NotifyMessage message) {
//        String logMessage = String.format("Received %s operation for user: %s", message.getOperation(), message.getUsername());
//        System.out.println(logMessage);

        // Exemplo de log variado
        if ("CREATE".equals(message.getOperation())) {
            System.out.println(message.getOperation() + " user: " + message.getUsername());
        } else if ("UPDATE".equals(message.getOperation())) {
            System.out.println(message.getOperation() + " user: " + message.getUsername());
        }
    }
}