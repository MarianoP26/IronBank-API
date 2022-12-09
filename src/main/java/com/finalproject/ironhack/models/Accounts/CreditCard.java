package com.finalproject.ironhack.models.Accounts;

import com.finalproject.ironhack.consts.Consts;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Accounts.interfaces.InterestAccount;
import com.finalproject.ironhack.utils.TimeHelperClass;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
public class CreditCard extends Account implements InterestAccount {

    @NotNull
    @DecimalMin(value = Consts.CREDITCARD_MIN_CREDIT_LIMIT, message = Consts.CREDITCARD_MIN_CREDIT_LIMIT_ERROR)
    @DecimalMax(value = Consts.CREDITCARD_MAX_CREDIT_LIMIT, message = Consts.CREDITCARD_MAX_CREDIT_LIMIT_ERROR)
    private BigDecimal creditLimit = new BigDecimal(Consts.CREDITCARD_DEFAULT_CREDIT_LIMIT);

    @NotNull
    @DecimalMin(value = Consts.CREDITCARD_MIN_INTEREST_RATE, message = Consts.CREDITCARD_MIN_INTEREST_RATE_ERROR)
    @DecimalMax(value = Consts.CREDITCARD_MAX_INTEREST_RATE, message = Consts.CREDITCARD_MAX_INTEREST_RATE_ERROR)
    private BigDecimal interestRate = new BigDecimal(Consts.CREDITCARD_DEFAULT_INTEREST_RATE);

    private Date lastInterestDate;

    public CreditCard(BigDecimal balance, AccountHolder accountHolder, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, accountHolder);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        setLastInterestDate(getCreationDate());
    }

    public CreditCard(){}

    public static boolean isCreditCardAccount(Account account){
        return account instanceof CreditCard;
    }

    @Override
    public void applyPenaltyFee(BigDecimal fee) {
        // Does not apply to CreditCard
    }

    @Override
    public void checkForPenalty() {
        // Does not apply to CreditCard
    }

    @Override
    public void freezeAccount(){
        // Does not apply to CreditCard
    }



    @Override
    public boolean checkForFraudTransactionTimes(String transactionTriggeredTime) {
        try{
            setLastTransactionTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(transactionTriggeredTime));
        }
        catch (ParseException e) {
            System.err.println("error");
        }
        return false;
    }

    @Override
    public void applyInterest() {
        int monthsPassed = TimeHelperClass.getDiffMonths(new Date(), getLastInterestDate());
        if (monthsPassed >= 1) {
            setBalance(getBalance().add((getBalance().multiply(interestRate.divide(BigDecimal.valueOf(12)))).multiply(BigDecimal.valueOf(monthsPassed))));
            try{
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String dateString = f.format(new Date());
                setLastInterestDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateString));
            } catch (ParseException e) {
                System.err.println("error parsing date @applyInterest");
            }
        }
    }
}
