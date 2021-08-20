package com.example.flightsbookingsystem.services;

import com.example.flightsbookingsystem.models.Admin;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;

import java.sql.Date;

public interface AdminService {
    Admin saveAdmin (Admin admin);
    Admin getAdmin (String userName);
    Role  saveRole  (Role role);
    void addRoleToAdmin (String userName, String roleName);

    Flight saveFlight (Flight flight);
    Flight getFlight  (long flightId);
    Flight updateFlightArrivePlace (long flightId, String newArrivePlace);
    Flight updateFlightDeparturePlace (long flightId, String newDeparturePlace);
    Flight updateFlightDate (long flightId, Date newDate);
    void deleteFlight (long flightId);
}
