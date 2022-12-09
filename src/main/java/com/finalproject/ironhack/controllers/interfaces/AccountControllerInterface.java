package com.finalproject.ironhack.controllers.interfaces;

import com.finalproject.ironhack.DTOs.AccountGenericDTO;
import com.finalproject.ironhack.DTOs.HolderIndexAccountIndexDTO;
import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Agents.AccountHolder;

import java.math.BigDecimal;
import java.util.List;

public interface AccountControllerInterface {



    List<Account> getAllAccounts();
    Account getAccountByAccountHolder(Long id);
    Account getAccountById(Long id);

    Account updateAccountBalance(Long id, BigDecimal newBalance);

    Account addAccount(AccountGenericDTO data);

    AccountHolder setSecondaryOwner(HolderIndexAccountIndexDTO data);

    void deleteAccount(Long id);

}
