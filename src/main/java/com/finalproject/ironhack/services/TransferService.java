package com.finalproject.ironhack.services;

import com.finalproject.ironhack.DTOs.*;
import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Accounts.CreditCard;
import com.finalproject.ironhack.models.Accounts.Savings;
import com.finalproject.ironhack.models.Accounts.enums.Status;
import com.finalproject.ironhack.models.Accounts.interfaces.InterestAccount;
import com.finalproject.ironhack.models.Accounts.interfaces.SecretKeyStatusAccount;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Transfer;
import com.finalproject.ironhack.repositories.AccountHolderRepository;
import com.finalproject.ironhack.repositories.AccountRepository;
import com.finalproject.ironhack.repositories.TransferRepository;
import com.finalproject.ironhack.services.interfaces.TransferServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static com.finalproject.ironhack.consts.Consts.*;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService implements TransferServiceInterface {

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer getTransferById(Long id) {
        return transferRepository.findById(id).get();
    }

    @Override
    public List<Transfer> getAllTransferFromAccount(Long id) {
        Account account = accountRepository.findById(id).get();
        return transferRepository.findByOrigin(account);
    }

    @Override
    public List<Transfer> findByAmountHigherThan(BigDecimal amount) {
        return transferRepository.findByAmountGreaterThan(amount);
    }

    @Override
    public TransferResultAccountToAccountDTO transferFromAccountToAccount(UserDetails userDetails, TransferHolderToAccountDTO data) {
        if(userDetails == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AGENT_MUST_BE_LOGGED_IN_ERROR);
        AccountHolder accountHolderOrigin = accountHolderRepository.findByName(userDetails.getUsername());
        Account accountOrigin = accountRepository.getAccountByAccountHolder(accountHolderOrigin);
        Account accountDestiny = accountRepository.findById(data.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TRANSFER_DESTINY_ACCOUNT_NOT_FOUND_ERROR));

        //Check if origin and destiny is a creditcard
        boolean isCreditCardOrigin = CreditCard.isCreditCardAccount(accountOrigin);
        boolean isCreditCardDestiny = CreditCard.isCreditCardAccount(accountDestiny);

        //Check if account origin is savings or creditcard to apply interests
        if (Savings.isSavingsAccount(accountOrigin) || isCreditCardOrigin) {
            InterestAccount accountOriginInterestInterface = (InterestAccount) accountOrigin;
            accountOriginInterestInterface.applyInterest();
        }
        if (Savings.isSavingsAccount(accountDestiny) || isCreditCardDestiny) {
            InterestAccount accountOriginInterestInterface = (InterestAccount) accountDestiny;
            accountOriginInterestInterface.applyInterest();
        }

        //Cast to interface wrapper if possible
        SecretKeyStatusAccount accountOriginWrapper = Account.wrapToSecretKeyStatusAccount(isCreditCardOrigin, accountOrigin);
        SecretKeyStatusAccount accountDestinyWrapper = Account.wrapToSecretKeyStatusAccount(isCreditCardDestiny, accountDestiny);

        if (!isCreditCardOrigin && !isCreditCardDestiny) {
            if (data.getName().equals(accountDestiny.getAccountHolder().getName()) && accountOrigin.getBalance().compareTo(data.getAmount()) > 0 && accountOriginWrapper.getStatus() != Status.FROZEN && accountDestinyWrapper.getStatus() != Status.FROZEN){
                accountOrigin.setBalance(accountOrigin.getBalance().subtract(data.getAmount()));
                accountDestiny.setBalance(accountDestiny.getBalance().add(data.getAmount()));
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_ACCOUNT_TO_ACCOUNT_ERROR);
            }
        }
        else if (!isCreditCardOrigin) {
            if (data.getName().equals(accountDestiny.getAccountHolder().getName()) && accountOrigin.getBalance().compareTo(data.getAmount()) > 0 && accountOriginWrapper.getStatus() != Status.FROZEN) {
                accountOrigin.setBalance(accountOrigin.getBalance().subtract(data.getAmount()));
                accountDestiny.setBalance(accountDestiny.getBalance().add(data.getAmount()));
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_ACCOUNT_TO_ACCOUNT_ERROR);
            }
        }
        else if (!isCreditCardDestiny) {
            if (data.getName().equals(accountDestiny.getAccountHolder().getName()) && accountOrigin.getBalance().compareTo(data.getAmount()) > 0 && accountDestinyWrapper.getStatus() != Status.FROZEN) {
                accountOrigin.setBalance(accountOrigin.getBalance().subtract(data.getAmount()));
                accountDestiny.setBalance(accountDestiny.getBalance().add(data.getAmount()));
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_ACCOUNT_TO_ACCOUNT_ERROR);
            }
        }
        else {
            if (data.getName().equals(accountDestiny.getAccountHolder().getName()) && accountOrigin.getBalance().compareTo(data.getAmount()) > 0) {
                accountOrigin.setBalance(accountOrigin.getBalance().subtract(data.getAmount()));
                accountDestiny.setBalance(accountDestiny.getBalance().add(data.getAmount()));
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_ACCOUNT_TO_ACCOUNT_ERROR);
            }
        }

        //Check for penalty fee
        accountOrigin.checkForPenalty();

        //Fraud system
        Account.fraudCheck(accountOrigin);
        Account.fraudCheck(accountDestiny);

        //save
        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestiny);
        transferRepository.save(new Transfer(data.getAmount(), accountOrigin, accountDestiny));

        return new TransferResultAccountToAccountDTO(accountOrigin.getAccountHolder().getName(), accountOrigin.getBalance(),
                accountDestiny.getAccountHolder().getName(), accountDestiny.getBalance());
    }

    @Override
    public TransferThirdPartyResultDTO transferFromThirdPartyToAccount(TransferThirdPartyDTO data) {
        Account accountDestiny = accountRepository.findById(data.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TRANSFER_DESTINY_ACCOUNT_NOT_FOUND_ERROR));

        boolean isCreditCard = CreditCard.isCreditCardAccount(accountDestiny);
        SecretKeyStatusAccount account = null;

        //Check if account origin is savings or creditcard to apply interests
        if (Savings.isSavingsAccount(accountDestiny) || isCreditCard) {
            InterestAccount accountOriginInterestInterface = (InterestAccount) accountDestiny;
            accountOriginInterestInterface.applyInterest();
        }

        if(!isCreditCard) {
            account = (SecretKeyStatusAccount) accountDestiny;
        }

        if(!isCreditCard && account != null) {
            if (account.getStatus() == Status.FROZEN) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_GENERAL_FROZEN_ERROR);
            if (BCrypt.checkpw(data.getSecretKey(), account.getSecretKey())) accountDestiny.setBalance(accountDestiny.getBalance().add(data.getAmount()));
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_GENERAL_SECRET_KEY_ERROR);
        }else if(isCreditCard){
            accountDestiny.setBalance(accountDestiny.getBalance().add(data.getAmount()));
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_TP_TO_ACCOUNT_ERROR);
        }
        accountDestiny.checkForPenalty();
        Account.fraudCheck(accountDestiny);
        accountDestiny = accountRepository.save(accountDestiny);
        transferRepository.save(new Transfer(data.getAmount(), null, accountDestiny));
        return new TransferThirdPartyResultDTO(accountDestiny.getAccountHolder().getName(), accountDestiny.getBalance());
    }

    @Override
    public TransferThirdPartyResultDTO transferFromAccountToThirdParty(TransferThirdPartyDTO data) {
        Account accountOrigin = accountRepository.findById(data.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TRANSFER_ORIGIN_ACCOUNT_NOT_FOUND_ERROR));

        boolean isCreditCard = CreditCard.isCreditCardAccount(accountOrigin);
        SecretKeyStatusAccount account = null;

        //Check if account origin is savings or creditcard to apply interests
        if (Savings.isSavingsAccount(accountOrigin) || isCreditCard) {
            InterestAccount accountOriginInterestInterface = (InterestAccount) accountOrigin;
            accountOriginInterestInterface.applyInterest();
        }

        if(!isCreditCard) {
            account = (SecretKeyStatusAccount) accountOrigin;
        }
        if(!isCreditCard && account != null) {
            if (account.getStatus() == Status.FROZEN) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_GENERAL_FROZEN_ERROR);
            if (BCrypt.checkpw(data.getSecretKey(), account.getSecretKey())) accountOrigin.setBalance(accountOrigin.getBalance().subtract(data.getAmount()));
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TRANSFER_GENERAL_SECRET_KEY_ERROR);
        }else if (isCreditCard){
            accountOrigin.setBalance(accountOrigin.getBalance().subtract(data.getAmount()));
        }
        accountOrigin.checkForPenalty();
        Account.fraudCheck(accountOrigin);
        accountOrigin = accountRepository.save(accountOrigin);
        transferRepository.save(new Transfer(data.getAmount(), accountOrigin, null));
        return new TransferThirdPartyResultDTO(accountOrigin.getAccountHolder().getName(), accountOrigin.getBalance());
    }


    @Override
    //Extra functionality: transfer from account to account without auth from any of the holders.
    //Method for admins or third party money transfer.
    //Ignores Status on both accounts.
    //Does NOT proc fraud system
    //Does not proc interest checking
    //Does not create a traceable transaction
    public void transferThirdPartyOrAdminFromAccountToAccount(TransferThirdPartyWithinSystemDTO data) {
        Account accountOrigin = accountRepository.findById(data.getFromId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TRANSFER_ORIGIN_ACCOUNT_NOT_FOUND_ERROR));
        Account accountDestiny = accountRepository.findById(data.getToId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TRANSFER_DESTINY_ACCOUNT_NOT_FOUND_ERROR));

        boolean isCreditCard = CreditCard.isCreditCardAccount(accountDestiny);
        SecretKeyStatusAccount account = null;

        if(!isCreditCard) {
            account = (SecretKeyStatusAccount) accountDestiny;
        }

        if(!isCreditCard && accountOrigin.getBalance().compareTo(data.getAmount()) > 0  && passwordEncoder.encode(data.getSecretKey()).equals(account.getSecretKey())) {
            accountOrigin.setBalance(accountOrigin.getBalance().subtract(data.getAmount()));
            accountDestiny.setBalance(accountDestiny.getBalance().add(data.getAmount()));

        }
        else if(isCreditCard && accountOrigin.getBalance().compareTo(data.getAmount()) > 0){
            accountOrigin.setBalance(accountOrigin.getBalance().subtract(data.getAmount()));
            accountDestiny.setBalance(accountDestiny.getBalance().add(data.getAmount()));
        }
        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestiny);
    }
}
