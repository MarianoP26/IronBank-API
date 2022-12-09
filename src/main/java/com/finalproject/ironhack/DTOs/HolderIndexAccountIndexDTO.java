package com.finalproject.ironhack.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolderIndexAccountIndexDTO {
    private Long holderId;
    private Long accountId;
}
