package br.com.fullcycle.api;

import br.com.fullcycle.dto.BalanceDTO;
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
@KafkaListener(topics = "balances", groupId = "wallet")
@RequiredArgsConstructor
public class BalanceConsumer {

    private static final Logger logger = LoggerFactory.getLogger(BalanceConsumer.class);

    private final BalanceService balanceService;

    @KafkaHandler
    public void updateBalances(String message) {
        try {
            logger.info("Balances {}", message);
            int jsonStart = message.indexOf("{");
            String jsonString = message.substring(jsonStart);
            ObjectMapper mapper = new ObjectMapper();
            BalanceDTO dto = mapper.readValue(jsonString, BalanceDTO.class);
            logger.info("Balances Converted {}", dto);
            balanceService.updateBalances(dto);
        } catch (JsonProcessingException e) {
            logger.error("Could not parse a message to dto");
        }
    }

}
