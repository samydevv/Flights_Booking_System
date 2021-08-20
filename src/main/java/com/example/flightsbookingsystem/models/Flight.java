package com.example.flightsbookingsystem.models;

import java.util.Date;
import java.util.UUID;

public class Flight {
    private final UUID id;
    private final long fare ;
    private final String from;
    private final String to;
    private final Date date;

    public Flight(UUID id, long fare, String from, String to, Date date) {
        this.id = id;
        this.fare = fare;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public long getFare() {
        return fare;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }
}
