package com.finalproject.ironhack.models.Accounts;

import com.finalproject.ironhack.models.Accounts.interfaces.SecretKeyStatusAccount;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Accounts.enums.Status;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
@Entity
@Data
public class StudentChecking extends Account implements SecretKeyStatusAccount {


    @NotNull
    private String secretKey;

    @NotNull
    private Status status;

    public StudentChecking(BigDecimal balance, AccountHolder accountHolder, String secretKey) {
        super(balance, accountHolder);
        this.secretKey = secretKey;
        status = Status.ACTIVE;
        setLastTransactionTime(getCreationDate());
    }

    public StudentChecking(){}

    @Override
    public void applyPenaltyFee(BigDecimal fee) {
        // Does not apply to studentChecking
    }

    @Override
    public void checkForPenalty() {
        // Does not apply to studentChecking
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

    @Override
    public void freezeAccount(){
        setStatus(Status.FROZEN);
    }

}
