package com.finalproject.ironhack.repositories;

import com.finalproject.ironhack.models.Agents.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByName(String name);

}
