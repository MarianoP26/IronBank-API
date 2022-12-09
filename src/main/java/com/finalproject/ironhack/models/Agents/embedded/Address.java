package com.finalproject.ironhack.models.Agents.embedded;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {

    private String address;


    public Address() {
    }

    public Address(String address) {
        this.address = address;
    }
}
