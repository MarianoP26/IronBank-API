package com.finalproject.ironhack.services;

import com.finalproject.ironhack.DTOs.AccountGenericDTO;
import com.finalproject.ironhack.DTOs.HolderIndexAccountIndexDTO;
import com.finalproject.ironhack.models.Accounts.*;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.repositories.AccountHolderRepository;
import com.finalproject.ironhack.repositories.AccountRepository;
import com.finalproject.ironhack.services.interfaces.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static com.finalproject.ironhack.consts.Consts.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountByAccountHolder(Long id) {
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ACCOUNT_ACCOUNT_NOT_FOUND_ERROR));
        return accountRepository.getAccountByAccountHolder(accountHolder);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ACCOUNT_ACCOUNT_NOT_FOUND_ERROR));
    }

    @Override
    public Account updateAccountBalance(Long id, BigDecimal newBalance) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ACCOUNT_ACCOUNT_NOT_FOUND_ERROR));
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    @Override
    public AccountHolder setSecondaryOwner(HolderIndexAccountIndexDTO data){
        Account selectedAccount = accountRepository.findById(data.getAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ACCOUNT_ACCOUNT_NOT_FOUND_ERROR));
        AccountHolder accountHolder = accountHolderRepository.findById(data.getHolderId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ACCOUNT_ACCOUNT_NOT_FOUND_ERROR));
        accountHolder.setSecondaryAccount(selectedAccount);
        return accountHolderRepository.save(accountHolder);
    }

    @Override
    public Account addAccount(AccountGenericDTO data) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(data.getAccountHolderId());
        Account account = switch (data.getType()) {
            case CHECKING_ACCOUNT -> accountHolder.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ACCOUNT_ACCOUNT_HOLDER_NOT_FOUND_ERROR)).getAge() < 24
                    ? accountRepository.save(new StudentChecking(data.getBalance(), accountHolder.get(), data.getSecretKey()))
                    : accountRepository.save(new Checking(data.getBalance(), accountHolder.get(), data.getSecretKey()));
            case SAVINGS_ACCOUNT ->
                    accountRepository.save(new Savings(data.getBalance(), accountHolder.get(), data.getSecretKey(), data.getMinimumBalance(), data.getInterestRate()));
            case CREDITCARD_ACCOUNT ->
                    accountRepository.save(new CreditCard(data.getBalance(), accountHolder.get(), data.getCreditLimit(), data.getInterestRate()));
            default ->
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ACCOUNT_GENERIC_UNEXPECTED_ERROR);
        };
        accountHolder.get().setAccount(account);
        accountHolderRepository.save(accountHolder.get());
        return account;
    }

    @Override
    public void deleteAccount(Long id){
        accountRepository.deleteById(id);
    }

}
