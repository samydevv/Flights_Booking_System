package com.example.flightsbookingsystem.models;

import java.util.UUID;

public class Admin {
    private final UUID id ;
    private final String userName;
    private final String password;

    public Admin(UUID id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
