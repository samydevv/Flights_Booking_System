package com.example.flightsbookingsystem.services;

import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;
import com.example.flightsbookingsystem.repository.CustomerRepo;
import com.example.flightsbookingsystem.repository.FlightRepo;
import com.example.flightsbookingsystem.repository.RoleRepo;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor //Lombok, inject the repo to the constructor.
@Slf4j //Lombok, for logs.
@Transactional
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepo customerRepo;
    private final RoleRepo roleRepo;
    private final FlightRepo flightRepo;

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("saving new admin {} into database", customer);
        return customerRepo.save(customer);
    }

    @Override
    public Customer getCustomer(String userName) {
        return customerRepo.findByUserName(userName);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} into database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToCustomer(String userName, String roleName) {
        log.info("adding role {} to admin {}", roleName, userName);
        Customer customer = customerRepo.findByUserName(userName);
        Role role = roleRepo.findByName(roleName);
        customer.getRoles().add(role);
    }

    @Override
    public List<Flight> searchForFlight(String departurePlace, String arrivePlace) {
        log.info("searching for flight by location");
        return flightRepo.findFlightByArrivePlaceAndDeparturePlace(departurePlace, arrivePlace);
    }

    @Override
    public List<Flight> searchForFlight(long fare) {
        log.info("searching for flight by fare");
        return flightRepo.findFlightByFare(fare);
    }

    @Override
    public void bookFlight(String userName, Flight flight) {
        Customer customer = customerRepo.findByUserName(userName);
        customer.getFlights().add(flight);
        log.info("user whose name is {} has booked a flight with an id equal to {}",customer.getName(),flight.getId());
    }
    
    @Override
    public void upgradeFlight(String userName, Flight flight, String classType) {
        log.info("upgrading class type from {} to {}",flight.getClassType(),classType);
        Customer customer = customerRepo.findByUserName(userName);
        flight.setClassType(classType);
    }

    @Override
    public void cancelFlight(String userName, Flight flight) {
        Customer customer = customerRepo.findByUserName(userName);
        if (customer.getFlights().remove(flight)) {
            log.info("Flight cancel successfully...");
        } else {
            log.info("Flight did not cancel");
        }
    }
}
