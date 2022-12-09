package com.finalproject.ironhack.repositories;

import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {


    List<Transfer> findByOrigin(Account account); //Todo: findby destination

    List<Transfer> findByAmountGreaterThan(BigDecimal amount);
}
