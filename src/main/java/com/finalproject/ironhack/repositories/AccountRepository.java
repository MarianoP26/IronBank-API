package com.finalproject.ironhack.repositories;

import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getAccountByAccountHolder(AccountHolder accountHolder);

}
