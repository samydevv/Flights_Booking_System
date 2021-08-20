package com.example.flightsbookingsystem.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Customer {
    private final UUID id ;
    private final String name;
    private final String userName;
    private final String password;
    private Collection<Flight> flights = new ArrayList<>();

    public Customer(UUID id, String name, String userName, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Collection<Flight> flights) {
        this.flights = flights;
    }
}
