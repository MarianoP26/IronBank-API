package com.finalproject.ironhack.models.Agents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.ironhack.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Inheritance
public abstract class Agent {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "agent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    public Agent(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Agent() {
    }
}
