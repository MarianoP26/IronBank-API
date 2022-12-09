package com.finalproject.ironhack.DTOs;

import com.finalproject.ironhack.models.Accounts.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResultAccountToAccountDTO {

    private String holderOriginName;
    private BigDecimal holderOriginBalance;

    private String holderDestinyName;
    private BigDecimal holderDestinyBalance;

}
