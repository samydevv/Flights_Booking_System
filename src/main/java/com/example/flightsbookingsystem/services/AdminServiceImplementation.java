package com.example.flightsbookingsystem.services;

import com.example.flightsbookingsystem.models.Admin;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;
import com.example.flightsbookingsystem.repository.AdminRepo;
import com.example.flightsbookingsystem.repository.FlightRepo;
import com.example.flightsbookingsystem.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
@RequiredArgsConstructor //Lombok, inject the repo to the constructor.
@Slf4j //Lombok, for logs.
@Transactional
public class AdminServiceImplementation implements AdminService{

    private final AdminRepo adminRepo;
    private final RoleRepo roleRepo;
    private final FlightRepo flightRepo;

    @Override
    public Admin saveAdmin(Admin admin) {
        log.info("saving new admin {} into database",admin);
        return adminRepo.save(admin);
    }

    @Override
    public Admin getAdmin(String userName) {
        return adminRepo.findByUserName(userName);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} into database",role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToAdmin(String userName, String roleName) {
        log.info("adding role {} to admin {}",roleName,userName);
        Admin admin =  adminRepo.findByUserName(userName);
        Role role = roleRepo.findByName(roleName);
        admin.getRoles().add(role);
    }

    @Override
    public Flight saveFlight(Flight flight) {
        log.info("saving new flight {} into database",flight);
        return flightRepo.save(flight);
    }

    @Override
    public Flight getFlight(long flightId) {
        return flightRepo.getById(flightId);
    }

    @Override
    public Flight updateFlightArrivePlace(long flightId, String newArrivePlace) {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight arrive place from {} to {} ",flight.getArrivePlace(),newArrivePlace);
        flight.setArrivePlace(newArrivePlace);
        return flight;
    }

    @Override
    public Flight updateFlightDeparturePlace(long flightId, String newDeparturePlace) {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight departure place from {} to {} ",flight.getDeparturePlace(),newDeparturePlace);
        flight.setDeparturePlace(newDeparturePlace);
        return flight;
    }

    @Override
    public Flight updateFlightDate(long flightId, Date newDate) {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight date from {} to {} ",flight.getDate(),newDate);
        flight.setDate(newDate);
        return flight;
    }

    @Override
    public void deleteFlight(long flightId) {
        log.info("deleting flight from database whose id = {}",flightId);
    flightRepo.deleteById(flightId);
    }
}
