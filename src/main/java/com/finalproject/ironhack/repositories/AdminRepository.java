package com.finalproject.ironhack.repositories;

import com.finalproject.ironhack.models.Agents.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
