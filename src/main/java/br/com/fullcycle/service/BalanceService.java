package br.com.fullcycle.service;

import br.com.fullcycle.dto.TransactionDTO;
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
    public void updateBalance(TransactionDTO dto) {
        logger.info("Transaction {}", dto);
        BigDecimal transactionAmount = BigDecimal.valueOf(dto.getPayload().getAmount());
        updateOrCreateBalance(dto.getPayload().getAccountIdFrom(), transactionAmount, false);
        updateOrCreateBalance(dto.getPayload().getAccountIdTo(), transactionAmount, true);

    }

    @Transactional(readOnly = true)
    public Balance findByAccountId(String accountId) {
        return balanceRepository.findByAccountId(accountId);
    }

    private void updateOrCreateBalance(String accountId, BigDecimal amount, boolean isCredit) {
        logger.info("Account {}", accountId);
        logger.info("Amount {}", amount);
        logger.info("Credit ? {}", isCredit);
        Balance balance = balanceRepository.findByAccountId(accountId);
        if (balance != null) {
            BigDecimal updatedBalance = isCredit
                    ? balance.getBalanceAccount().add(amount)
                    : balance.getBalanceAccount().subtract(amount);
            balance.setBalanceAccount(updatedBalance);
            logger.info("Updating a Balance {}", balance);
            balanceRepository.save(balance);
        } else {
            BigDecimal initialBalance = isCredit ? amount : amount.negate();
            logger.info("Creating a New Balance {}", initialBalance);
            balanceRepository.save(
                    Balance.builder()
                            .id(UUID.randomUUID().toString())
                            .accountId(accountId)
                            .balanceAccount(initialBalance)
                            .build()
            );
        }
    }
}
