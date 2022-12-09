package com.finalproject.ironhack.models;

import com.finalproject.ironhack.models.Agents.Agent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String role;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    public Role(String role, Agent agent) {
        this.role = role;
        this.agent = agent;
    }

    public Role() {
    }
}
