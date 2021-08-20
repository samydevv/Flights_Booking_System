package com.example.flightsbookingsystem.repository;

import com.example.flightsbookingsystem.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Long> {
}
