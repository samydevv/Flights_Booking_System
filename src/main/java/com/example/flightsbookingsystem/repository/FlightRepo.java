package com.example.flightsbookingsystem.repository;

import com.example.flightsbookingsystem.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepo extends JpaRepository<Flight,Long> {
    List<Flight> findFlightByDeparturePlaceAndArrivePlace (String departurePlace, String arrivePlace);
    List<Flight> findFlightByFareIsLessThanEqual (long fare);
}

