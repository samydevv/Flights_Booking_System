package com.example.flightsbookingsystem.repository;

import com.example.flightsbookingsystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}

