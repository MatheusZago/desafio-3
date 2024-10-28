package br.com.compass.api.kafka;

import br.com.compass.api.model.NotifyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    //Classe feita para produzir as mensagens

    private static final String TOPIC = "notify_topic";

    @Autowired
    private KafkaTemplate<String, NotifyMessage> kafkaTemplate;

    public void sendMessage(NotifyMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
