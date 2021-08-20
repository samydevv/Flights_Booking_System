package com.example.flightsbookingsystem.repository;

import com.example.flightsbookingsystem.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepo extends JpaRepository<Flight,Long> {
}

