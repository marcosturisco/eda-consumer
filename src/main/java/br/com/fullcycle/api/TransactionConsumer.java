package br.com.fullcycle.api;

import br.com.fullcycle.dto.TransactionDTO;
import br.com.fullcycle.service.BalanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "transactions", groupId = "wallet")
@RequiredArgsConstructor
public class TransactionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TransactionConsumer.class);

    private final BalanceService balanceService;

    @KafkaHandler
    public void getTransaction(String message) {
        try {
            logger.info("Transaction {}", message);
            int jsonStart = message.indexOf("{");
            String jsonString = message.substring(jsonStart);
            ObjectMapper mapper = new ObjectMapper();
            TransactionDTO dto = mapper.readValue(jsonString, TransactionDTO.class);
            logger.info("Transaction Converted {}", dto);
            balanceService.updateBalance(dto);
        } catch (JsonProcessingException e) {
            logger.error("Could not parse a message to dto");
        }
    }

}
