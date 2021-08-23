package com.example.flightsbookingsystem.controller;

import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.services.CustomerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerAPI extends RoleTo {
    private final CustomerService customerService;

    @PostMapping("/saveCustomer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/customer/saveCustomer").toUriString());
        return ResponseEntity.created(uri).body(customerService.saveCustomer(customer));
    }

    @GetMapping ("/searchFlightByFare/{fare}")
    public ResponseEntity<List<Flight>> searchFlightByFare(@PathVariable long fare){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/customer/searchFlightByFare").toUriString());
        return ResponseEntity.created(uri).body(customerService.searchForFlight(fare));
    }

    @GetMapping ("/searchFlightByLocations/{departurePlace}/{arrivePlace}")
    public ResponseEntity<List<Flight>> searchFlightByLocations(@PathVariable String departurePlace, @PathVariable String arrivePlace){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/customer/searchFlightByLocations").toUriString());
        return ResponseEntity.created(uri).body(customerService.searchForFlight(departurePlace,arrivePlace));
    }

    @PostMapping ("/bookFlight/{userName}/{flightId}")
    public ResponseEntity bookFlight(@PathVariable String userName, @PathVariable Long flightId){
        customerService.bookFlight(userName,flightId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/upgradeFlight/{userName}/{flightId}/{classType}")
    public ResponseEntity upgradeFlight(@PathVariable String userName, @PathVariable Long flightId, @PathVariable String classType){
        customerService.upgradeFlight(userName,flightId,classType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cancelFlight/{userName}/{flightId}")
    public ResponseEntity cancelFlight(@PathVariable String userName, @PathVariable Long flightId){
        customerService.cancelFlight(userName,flightId);
        return ResponseEntity.ok().build();
    }
}
