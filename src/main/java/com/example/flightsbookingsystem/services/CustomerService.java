package com.example.flightsbookingsystem.services;

import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer getCustomer(String userName);
    Role saveRole  (Role role);
    void addRoleToCustomer (String userName, String roleName);
    List<Flight> searchForFlight(String departurePlace, String arrivePlace);
    List<Flight> searchForFlight(long fare);
    void bookFlight(String userName, Flight flight);
    void upgradeFlight(String userName, Flight flight, String  classType);
    void cancelFlight(String userName, Flight flight);
}
