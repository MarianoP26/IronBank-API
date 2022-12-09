package com.finalproject.ironhack.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferThirdPartyResultDTO {
    private String name;
    private BigDecimal amount;
}
