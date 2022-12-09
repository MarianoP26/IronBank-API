package com.finalproject.ironhack.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountGenericDTO {

    //Super
    private String type;
    private Long accountHolderId;
    private BigDecimal balance;

    //Checking
    private String secretKey;

    //CreditCard
    private BigDecimal creditLimit;

    //CreditCard & Savings
    private BigDecimal interestRate;

    //Saving
    private BigDecimal minimumBalance;


}
