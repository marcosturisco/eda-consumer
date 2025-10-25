package br.com.fullcycle.service;

import br.com.fullcycle.dto.BalanceDTO;
import br.com.fullcycle.entity.Balance;
import br.com.fullcycle.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);

    private final BalanceRepository balanceRepository;

    @Transactional
    public void updateBalances(BalanceDTO dto) {
        logger.info("Balances {}", dto);
        BigDecimal balanceFromAccount = BigDecimal.valueOf(dto.getPayload().getBalanceAccountIdFrom());
        BigDecimal balanceToAccount = BigDecimal.valueOf(dto.getPayload().getBalanceAccountIdTo());
        updateBalance(dto.getPayload().getAccountIdFrom(), balanceFromAccount);
        updateBalance(dto.getPayload().getAccountIdTo(), balanceToAccount);
    }

    @Transactional(readOnly = true)
    public Balance findByAccountId(String accountId) {
        return balanceRepository.findByAccountId(accountId);
    }

    private void updateBalance(String accountId, BigDecimal amount) {
        logger.info("Account {}", accountId);
        logger.info("Balance {}", amount);
        Balance balance = balanceRepository.findByAccountId(accountId);
        if (balance != null) {
            balance.setBalanceAccount(amount);
            logger.info("Updating a Balance {}", balance);
            balanceRepository.save(balance);
        } else {
            logger.info("Creating a New Balance {}", amount);
            balanceRepository.save(
                    Balance.builder()
                            .id(UUID.randomUUID().toString())
                            .accountId(accountId)
                            .balanceAccount(amount)
                            .build()
            );
        }
    }
}
