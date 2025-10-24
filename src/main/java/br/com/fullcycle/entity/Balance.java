package br.com.fullcycle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

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

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "balance")
    private BigDecimal balanceAccount;

}
