package com.finalproject.ironhack.models.Accounts.interfaces;

import java.math.BigDecimal;

public interface BankAccount {

    void checkForPenalty();
    void applyPenaltyFee(BigDecimal fee);


}
