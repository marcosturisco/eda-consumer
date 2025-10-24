package br.com.fullcycle.service;

import br.com.fullcycle.dto.TransactionDTO;
import br.com.fullcycle.entity.Balance;
import br.com.fullcycle.repository.BalanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

        var fromId = dto.getPayload().getAccountIdFrom();
        Balance balanceFrom = balanceRepository.findById(fromId)
                .orElseGet(() -> {
                            String id = UUID.randomUUID().toString();
                            return balanceRepository.save(
                                    Balance.builder()
                                            .id(id)
                                            .accountId(fromId)
                                            .balanceAccount(BigDecimal.valueOf(dto.getPayload().getAmount()))
                                            .build());
                        }
                );

        var toId = dto.getPayload().getAccountIdTo();
        Balance balanceTo = balanceRepository.findById(toId)
                .orElseGet(() -> {
                            String id = UUID.randomUUID().toString();
                            return balanceRepository.save(
                                    Balance.builder()
                                            .id(id)
                                            .accountId(toId)
                                            .balanceAccount(BigDecimal.valueOf(dto.getPayload().getAmount()))
                                            .build());
                        }
                );

        BigDecimal updateFromAccount = balanceFrom.getBalanceAccount()
                .subtract(BigDecimal.valueOf(dto.getPayload().getAmount()));
        balanceFrom.setBalanceAccount(updateFromAccount);
        BigDecimal updateToAccount = balanceTo.getBalanceAccount()
                .subtract(BigDecimal.valueOf(dto.getPayload().getAmount()));
        balanceTo.setBalanceAccount(updateToAccount);

        balanceRepository.save(balanceFrom);
        balanceRepository.save(balanceTo);
    }
}
