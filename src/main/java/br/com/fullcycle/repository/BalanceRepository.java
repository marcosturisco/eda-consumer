package br.com.fullcycle.repository;

import br.com.fullcycle.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, String> {

    @Query("SELECT bal FROM Balance bal WHERE bal.accountId = ?1")
    Balance findByAccountId(String accountId);

}
