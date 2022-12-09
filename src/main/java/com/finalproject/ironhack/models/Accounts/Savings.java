package com.finalproject.ironhack.models.Accounts;

import com.finalproject.ironhack.consts.Consts;
import com.finalproject.ironhack.models.Accounts.interfaces.SecretKeyStatusAccount;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Accounts.enums.Status;
import com.finalproject.ironhack.models.Accounts.interfaces.InterestAccount;
import com.finalproject.ironhack.utils.TimeHelperClass;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
@Data
public class Savings extends Account implements InterestAccount, SecretKeyStatusAccount {

    @NotNull
    private String secretKey;

    @NotNull
    @DecimalMin(value = Consts.SAVINGS_MIN_MINIMUM_BALANCE, message = Consts.SAVINGS_MIN_MINIMUM_BALANCE_ERROR)
    @DecimalMax(value = Consts.SAVINGS_MAX_MINIMUM_BALANCE, message = Consts.SAVINGS_MAX_MINIMUM_BALANCE_ERROR)
    private BigDecimal minimumBalance = new BigDecimal(Consts.SAVINGS_DEFAULT_MINIMUM_BALANCE);

    @NotNull
    private Status status;

    @NotNull
    @DecimalMax(value = Consts.SAVINGS_MAX_INTEREST_RATE, message = Consts.SAVINGS_MAX_INTEREST_RATE_ERROR)
    private BigDecimal interestRate = new BigDecimal(Consts.SAVINGS_DEFAULT_INTEREST_RATE);

    private Date lastInterestDate;

    public Savings(BigDecimal balance, AccountHolder accountHolder, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, accountHolder);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        status = Status.ACTIVE;
        setLastInterestDate(getCreationDate());
    }

    public Savings(){}

    @Override
    public void applyPenaltyFee(BigDecimal fee) {
        setBalance(getBalance().subtract(fee));
    }

    @Override
    public void checkForPenalty() {
        if (getBalance().compareTo(getMinimumBalance()) < 0) {
            applyPenaltyFee(getPenaltyFee());
        }
    }

    @Override
    public void applyInterest() {
        int yearsPassed = TimeHelperClass.getDiffYears(new Date(), getLastInterestDate());
        if (yearsPassed >= 1) {
            setBalance(getBalance().add((getBalance().multiply(interestRate)).multiply(BigDecimal.valueOf(yearsPassed))));
            try{
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String dateString = f.format(new Date());
                setLastInterestDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateString));
            } catch (ParseException e) {
                System.err.println("error parsing date @applyInterest");
            }
        }
    }

    @Override
    public String getSecretKey(){
        return secretKey;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void freezeAccount(){
        setStatus(Status.FROZEN);
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    public static boolean isSavingsAccount(Account account){
        return account instanceof Savings;
    }

}
