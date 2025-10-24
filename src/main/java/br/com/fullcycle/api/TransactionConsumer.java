package br.com.fullcycle.api;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "transactions", groupId = "wallet")
public class TransactionConsumer {

    @KafkaHandler
    public void getTransaction(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
    }

}
