package com.finalproject.ironhack.models.Accounts;

import com.finalproject.ironhack.consts.Consts;
import com.finalproject.ironhack.models.Accounts.interfaces.SecretKeyStatusAccount;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.utils.TimeHelperClass;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import com.finalproject.ironhack.models.Accounts.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
@Data
public class Checking extends Account implements SecretKeyStatusAccount {

    @NotNull
    private String secretKey;

    @NotNull
    private BigDecimal minimumBalance = new BigDecimal(Consts.CHECKING_MINIMUM_BALANCE);

    @NotNull
    private BigDecimal monthlyMaintenanceFee = new BigDecimal(Consts.CHECKING_MONTHLY_MAINTENANCE_FEE);

    @NotNull
    private Status status;

    public Checking(BigDecimal balance, AccountHolder accountHolder, String secretKey) {
        super(balance, accountHolder);
        this.secretKey = secretKey;
        status = Status.ACTIVE;
        setLastTransactionTime(getCreationDate());
    }

    public Checking() {
    }

    @Override
    public void checkForPenalty() {
        if (getBalance().compareTo(getMinimumBalance()) < 0) {
            applyPenaltyFee(getPenaltyFee());
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

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    public void freezeAccount(){
        setStatus(Status.FROZEN);
    }

    @Override
    public void applyPenaltyFee(BigDecimal fee) {
        setBalance(getBalance().subtract(fee));
    }

}
