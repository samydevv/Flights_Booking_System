package com.example.flightsbookingsystem.services;

import com.example.flightsbookingsystem.models.Admin;
import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

public interface AdminService {
    Admin saveAdmin (Admin admin);
    Admin getAdmin (String userName);
    List<Admin> getAdmins ();
    List<Customer> getCustomers ();
    Role  saveRole  (Role role);
    void addRoleToAdmin (String userName, String roleName);
    void addRoleToCustomer (String userName, String roleName);
    void saveFlight (Flight flight);
    Flight getFlight  (Long flightId);
    Flight updateFlightArrivePlace (Long flightId, String newArrivePlace);
    Flight updateFlightDeparturePlace (Long flightId, String newDeparturePlace);
    Flight updateFlightDate (Long flightId, String newDate) throws ParseException;
    void deleteFlight (Long flightId);
}
