package com.finalproject.ironhack.models;

import com.finalproject.ironhack.models.Accounts.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "origin_id")
    private Account origin;

    @OneToOne
    @JoinColumn(name = "destination_id")
    private Account destination;

    @NotNull
    private Date time;

    public Transfer(){

    }

    public Transfer(BigDecimal amount, Account origin, Account destination){
        this.amount = amount;
        this.origin = origin;
        this.destination = destination;
        time = new Date();
    }

}
