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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor //Lombok, inject the repo to the constructor.
@Slf4j //Lombok, for logs.
@Transactional
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepo customerRepo;
    private final FlightRepo flightRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("saving new admin {} into database", customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepo.save(customer);
    }

    @Override
    public Customer getCustomer(String userName) {
        return customerRepo.findByUserName(userName);
    }

    @Override
    public List<Flight> searchForFlight(String departurePlace, String arrivePlace) {
        log.info("searching for flight by location");
        return flightRepo.findFlightByDeparturePlaceAndArrivePlace(departurePlace, arrivePlace);
    }

    @Override
    public List<Flight> searchForFlight(long fare) {
        log.info("searching for flight by fare");
        return flightRepo.findFlightByFareIsLessThanEqual(fare);
    }

    @Override
    public void bookFlight(String userName, Long flightId) {
        Customer customer = customerRepo.findByUserName(userName);
        Flight flight = flightRepo.getById(flightId);
        customer.getFlights().add(flight);
        log.info("user whose name is {} has booked a flight with an id equal to {}", customer.getName(), flight.getId());
    }

    @Override
    public void upgradeFlight(String userName, Long flightId, String classType) {
        Flight flight = flightRepo.getById(flightId);
        log.info("upgrading class type from {} to {}", flight.getClassType(), classType);
        Customer customer = customerRepo.findByUserName(userName);
        flight.setClassType(classType);
        flight.setFare(flight.getFare() * 2);
    }

    @Override
    public void cancelFlight(String userName, Long flightId) {
        Flight flight = flightRepo.getById(flightId);
        Customer customer = customerRepo.findByUserName(userName);
        if (customer.getFlights().remove(flight)) {
            log.info("Flight cancel successfully...");
        } else {
            log.info("Flight did not cancel");
        }
    }
}
