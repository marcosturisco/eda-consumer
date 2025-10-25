package br.com.fullcycle.rest;

import br.com.fullcycle.dto.BalanceOutputDto;
import br.com.fullcycle.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balances")
@RequiredArgsConstructor
public class BalanceRestController {

    private final BalanceService balanceService;

    @GetMapping("/{account_id}")
    public ResponseEntity<BalanceOutputDto> exposeBalance(@PathVariable("account_id") String accountId) {
        var balance = balanceService.findByAccountId(accountId);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
