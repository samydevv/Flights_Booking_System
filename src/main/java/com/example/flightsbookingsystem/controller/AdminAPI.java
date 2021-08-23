package com.example.flightsbookingsystem.controller;

import com.example.flightsbookingsystem.models.Admin;
import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;
import com.example.flightsbookingsystem.services.AdminService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAPI extends RoleTo {
    private final AdminService adminService;

    @GetMapping("/getAllAdmins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok().body(adminService.getAdmins());
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok().body(adminService.getCustomers());
    }

    @PostMapping("/saveAdmin")
    public ResponseEntity<Admin> saveAdmins(@RequestBody Admin admin) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/admin/saveAdmin").toUriString());
        return ResponseEntity.created(uri).body(adminService.saveAdmin(admin));
    }

    @PostMapping("/role/saveRole")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/saveRole").toUriString());
        return ResponseEntity.created(uri).body(adminService.saveRole(role));
    }

    @PostMapping("/role/addRoleAdmin")
    public ResponseEntity<?> addRoleToAdmin(@RequestBody RoleTo form) {
        adminService.addRoleToAdmin(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role/addRoleCustomer")
    public ResponseEntity<?> addRoleToCustomer(@RequestBody RoleTo form) {
        adminService.addRoleToCustomer(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saveFlight")
    public ResponseEntity<?> saveFlight(@RequestBody Flight flight) {
        adminService.saveFlight(flight);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getFlight/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable("id") Long flightId) {
        return ResponseEntity.ok().body(adminService.getFlight(flightId));
    }

    @DeleteMapping("/deleteFlight/{id}")
    public ResponseEntity<?> deleteFlight(@PathVariable("id") Long flightId) {
        adminService.deleteFlight(flightId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateFlightArrivePlace/{id}/{newArrivePlace}")
    public ResponseEntity<?> updateFlightArrivePlace(@PathVariable("id") Long flightId, @PathVariable("newArrivePlace") String newArrivePlace) {
        adminService.updateFlightArrivePlace(flightId, newArrivePlace);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateFlightDeparturePlace/{id}/{newDeparturePlace}")
    public ResponseEntity<?> updateFlightDeparturePlace(@PathVariable("id") Long flightId, @PathVariable("newDeparturePlace") String newDeparturePlace) {
        adminService.updateFlightDeparturePlace(flightId, newDeparturePlace);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateFlightDate/{id}/{newDate}")
    public ResponseEntity<?> updateFlightDate(@PathVariable("id") Long flightId, @PathVariable("newDate") String newDate) throws ParseException {
        adminService.updateFlightDate(flightId, newDate);
        return ResponseEntity.ok().build();
    }
}


