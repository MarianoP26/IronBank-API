package com.finalproject.ironhack.controllers;

import com.finalproject.ironhack.DTOs.AccountGenericDTO;
import com.finalproject.ironhack.DTOs.HolderIndexAccountIndexDTO;
import com.finalproject.ironhack.controllers.interfaces.AccountControllerInterface;
import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController implements AccountControllerInterface {

    @Autowired
    AccountService accountService;

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/all")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Account getAccountByAccountHolder(@RequestParam Long id) {
        return accountService.getAccountByAccountHolder(id);
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @Override
    @PatchMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateAccountBalance(@PathVariable Long id, @RequestParam BigDecimal newBalance) {
        return accountService.updateAccountBalance(id, newBalance);
    }

    @PatchMapping("/accounts/set-secondary-owner")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder setSecondaryOwner(@RequestBody HolderIndexAccountIndexDTO data){
        return accountService.setSecondaryOwner(data);
    }

    @Override
    @PostMapping("/accounts/add")
    @ResponseStatus(HttpStatus.OK)
    public Account addAccount (@RequestBody AccountGenericDTO data) {
        return accountService.addAccount(data);
    }

    @Override
    @DeleteMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
