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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor //Lombok, inject the repo to the constructor.
@Slf4j //Lombok, for logs.
@Transactional
public class AdminServiceImplementation implements AdminService, UserDetailsService {

    private final AdminRepo adminRepo;
    private final RoleRepo roleRepo;
    private final FlightRepo flightRepo;
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByUserName(userName);
        Customer customer = customerRepo.findByUserName(userName);
        if (admin != null) {
            log.info("admin found in the database");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            admin.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(admin.getUserName(), admin.getPassword(), authorities);
        } else if (customer != null) {
            log.info("customer found in the database");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            customer.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(customer.getUserName(), customer.getPassword(), authorities);
        } else {
            log.error("not found in the database");
            throw new UsernameNotFoundException("admin not found in the database");
        }
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        log.info("saving new admin {} into database", admin);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
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
    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} into database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToAdmin(String userName, String roleName) {
        log.info("adding role {} to admin {}", roleName, userName);
        Admin admin = adminRepo.findByUserName(userName);
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
        log.info("saving new flight {} into database", flight);
        flightRepo.save(flight);
    }

    @Override
    public Flight getFlight(Long flightId) {
        log.info("get flight whose id ={} and it's data = {}", flightId, flightRepo.getById(flightId));
        return flightRepo.getById(flightId);
    }

    @Override
    public Flight updateFlightArrivePlace(Long flightId, String newArrivePlace) {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight arrive place from {} to {} ", flight.getArrivePlace(), newArrivePlace);
        flight.setArrivePlace(newArrivePlace);
        return flight;
    }

    @Override
    public Flight updateFlightDeparturePlace(Long flightId, String newDeparturePlace) {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight departure place from {} to {} ", flight.getDeparturePlace(), newDeparturePlace);
        flight.setDeparturePlace(newDeparturePlace);
        return flight;
    }

    @Override
    public Flight updateFlightDate(Long flightId, String newDate) throws ParseException {
        Flight flight = flightRepo.getById(flightId);
        log.info("updating flight date from {} to {} ", flight.getDate(), newDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf1.parse(newDate);
        java.sql.Date sqlNewDate = new java.sql.Date(date.getTime());
        log.info("after converting date type {} ", sqlNewDate);
        flight.setDate(sqlNewDate);
        return flight;
    }

    @Override
    public void deleteFlight(Long flightId) {
        log.info("deleting flight from database whose id = {}", flightId);
        flightRepo.deleteById(flightId);
    }

}
