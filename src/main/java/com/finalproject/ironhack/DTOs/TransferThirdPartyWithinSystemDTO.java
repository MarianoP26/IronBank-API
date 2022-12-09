package com.finalproject.ironhack.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferThirdPartyWithinSystemDTO {
    private Long fromId;
    private Long toId;
    private String secretKey;
    private BigDecimal amount;
}
