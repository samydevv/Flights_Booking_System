package com.example.flightsbookingsystem.services;

import com.example.flightsbookingsystem.models.Admin;
import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;
import com.example.flightsbookingsystem.repository.AdminRepo;
import com.example.flightsbookingsystem.repository.CustomerRepo;
import com.example.flightsbookingsystem.repository.FlightRepo;
import com.example.flightsbookingsystem.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor //Lombok, inject the repo to the constructor.
@Slf4j //Lombok, for logs.
@Transactional
public class AdminServiceImplementation implements AdminService{

    private final AdminRepo adminRepo;
    private final RoleRepo roleRepo;
    private final FlightRepo flightRepo;
    private final CustomerRepo customerRepo;

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
    public List<Admin> getAdmins() {
        return adminRepo.findAll();
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
    public void addRoleToCustomer(String userName, String roleName) {
        log.info("adding role {} to customer {}", roleName, userName);
        Customer customer = customerRepo.findByUserName(userName);
        Role role = roleRepo.findByName(roleName);
        customer.getRoles().add(role);
    }

    @Override
    public void saveFlight(Flight flight) {
        log.info("saving new flight {} into database",flight);
        flightRepo.save(flight);
    }

    @Override
    public Flight getFlight(Long flightId) {
        log.info("get flight whose id ={} and it's data = {}",flightId,flightRepo.getById(flightId));
        return flightRepo.getById(flightId);
    }

    @Override
    public Flight updateFlightArrivePlace(Long flightId, String newArrivePlace) {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight arrive place from {} to {} ",flight.getArrivePlace(),newArrivePlace);
        flight.setArrivePlace(newArrivePlace);
        return flight;
    }

    @Override
    public Flight updateFlightDeparturePlace(Long flightId, String newDeparturePlace) {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight departure place from {} to {} ",flight.getDeparturePlace(),newDeparturePlace);
        flight.setDeparturePlace(newDeparturePlace);
        return flight;
    }

    @Override
    public Flight updateFlightDate(Long flightId, String newDate) throws ParseException {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight date from {} to {} ",flight.getDate(),newDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf1.parse(newDate);
        java.sql.Date sqlNewDate = new java.sql.Date(date.getTime());
        log.info("after converting date type {} ",sqlNewDate);
        flight.setDate(sqlNewDate);
        return flight;
    }

    @Override
    public void deleteFlight(Long flightId) {
        log.info("deleting flight from database whose id = {}",flightId);
    flightRepo.deleteById(flightId);
    }
}
