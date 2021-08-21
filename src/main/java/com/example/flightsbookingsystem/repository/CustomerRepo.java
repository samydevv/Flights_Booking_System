package com.example.flightsbookingsystem.repository;

import com.example.flightsbookingsystem.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Customer findByUserName (String userName);

}

