package com.finalproject.ironhack.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferThirdPartyDTO {
    private Long id;
    private String secretKey;
    private BigDecimal amount;
}
