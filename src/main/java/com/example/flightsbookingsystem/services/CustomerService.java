package com.example.flightsbookingsystem.services;


import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;


import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Customer getCustomer(String userName);

    List<Flight> searchForFlight(String departurePlace, String arrivePlace);

    List<Flight> searchForFlight(long fare);

    void bookFlight(String userName, Long flightId);

    void upgradeFlight(String userName, Long flightId, String classType);

    void cancelFlight(String userName, Long flightId);
}
