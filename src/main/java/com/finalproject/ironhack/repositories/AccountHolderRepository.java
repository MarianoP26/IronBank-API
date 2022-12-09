package com.finalproject.ironhack.repositories;

import com.finalproject.ironhack.models.Agents.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    AccountHolder findByName(String name);

}
