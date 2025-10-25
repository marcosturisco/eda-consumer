package br.com.fullcycle.service;

import br.com.fullcycle.dto.BalanceInputDto;
import br.com.fullcycle.dto.BalanceOutputDto;
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
    public void updateBalances(BalanceInputDto dto) {
        logger.info("Balances {}", dto);
        BigDecimal balanceFromAccount = BigDecimal.valueOf(dto.getPayload().getBalanceAccountIdFrom());
        BigDecimal balanceToAccount = BigDecimal.valueOf(dto.getPayload().getBalanceAccountIdTo());
        logger.info("Creating a New From Balance {}", balanceFromAccount);
        logger.info("Creating a New To Balance {}", balanceToAccount);
        balanceRepository.save(
                Balance.builder()
                        .id(UUID.randomUUID().toString())
                        .accountIdFrom(dto.getPayload().getAccountIdFrom())
                        .accountIdTo(dto.getPayload().getAccountIdTo())
                        .balanceAccountIdFrom(balanceFromAccount)
                        .balanceAccountIdTo(balanceToAccount)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public BalanceOutputDto findByAccountId(String accountId) {
        var balance = balanceRepository.findTopByAccountIdFromOrAccountIdToOrderByCreatedAtDesc(accountId, accountId);
        return getOutputDto(accountId, balance);
    }

    private BalanceOutputDto getOutputDto(String accountId, Balance balance) {
        var amount = balance.getAccountIdFrom().equals(accountId)
                ? balance.getBalanceAccountIdFrom()
                : balance.getBalanceAccountIdTo();
        return BalanceOutputDto.builder().accountId(accountId).balance(amount).build();
    }
}
