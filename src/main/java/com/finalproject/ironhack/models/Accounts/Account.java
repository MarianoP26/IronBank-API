package com.finalproject.ironhack.models.Accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.ironhack.models.Accounts.interfaces.SecretKeyStatusAccount;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Accounts.interfaces.BankAccount;
import com.finalproject.ironhack.utils.TimeHelperClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Inheritance
public abstract class Account implements BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal balance;

    @NotNull
    @JsonIgnore
    @OneToOne(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private AccountHolder accountHolder;
    @OneToOne(mappedBy = "secondaryAccount", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private AccountHolder secondaryAccountHolder;

    @NotNull
    private Date creationDate;

    @NotNull
    private BigDecimal penaltyFee = new BigDecimal("40");

    private Date lastTransactionTime;

    public Account(BigDecimal balance, AccountHolder accountHolder) {
        this.balance = balance;
        this.accountHolder = accountHolder;
        setCreationDate();
    }

    public Account() {
    }


    @Override
    public abstract void applyPenaltyFee(BigDecimal fee);

    public abstract void checkForPenalty();

    public abstract void freezeAccount();

    public void setCreationDate() {
        try {
            creationDate = TimeHelperClass.f.parse(TimeHelperClass.f.format(new Date()));
        } catch (ParseException e) {
            System.err.println("Error while creating an account regarding Date.");
        }
    }

    public static SecretKeyStatusAccount wrapToSecretKeyStatusAccount(Boolean isCreditCard, Account account) {
        if (isCreditCard) return null;
        return (SecretKeyStatusAccount) account;
    }

    public static void fraudCheck(Account account) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(account.checkForFraudTransactionTimes(f.format(new Date()))){
            account.freezeAccount();
        }

    }
    public boolean checkForFraudTransactionTimes(String transactionTriggeredTime) {
        if (getLastTransactionTime() == null) {
            Date date = null;
            try {
                date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(transactionTriggeredTime));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            setLastTransactionTime(date);
            return false;
        }
        long secondsPassedBetween = TimeHelperClass.timePassedBetweenInSeconds(transactionTriggeredTime, getLastTransactionTime().toString());
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(transactionTriggeredTime));
            setLastTransactionTime(date);
            return secondsPassedBetween <= 1;
        }
        catch (ParseException e) {
            System.err.println("Error parseando man");
            return secondsPassedBetween <= 1;
        }
    }
}
