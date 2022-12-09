package com.finalproject.ironhack.models.Agents;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class ThirdParty extends Agent{

    @NotEmpty
    private String hashedKey;

    public ThirdParty(String name, String password) {
        super(name, password);
        hashedKey = password;
    }

    public ThirdParty() {
    }
}
