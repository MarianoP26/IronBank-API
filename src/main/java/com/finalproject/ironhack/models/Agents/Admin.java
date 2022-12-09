package com.finalproject.ironhack.models.Agents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.ironhack.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Admin extends Agent{

    @JsonIgnore
    @OneToMany(mappedBy = "agent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    public Admin(String name, String password) {
        super(name, password);
    }

    public Admin() {
        super();
    }
}
