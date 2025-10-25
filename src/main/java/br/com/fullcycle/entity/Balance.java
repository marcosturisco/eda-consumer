package br.com.fullcycle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "balances")
public class Balance {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "account_id_from")
    private String accountIdFrom;

    @Column(name = "account_id_to")
    private String accountIdTo;

    @Column(name = "balance_account_id_from")
    private BigDecimal balanceAccountIdFrom;

    @Column(name = "balance_account_id_to")
    private BigDecimal balanceAccountIdTo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
