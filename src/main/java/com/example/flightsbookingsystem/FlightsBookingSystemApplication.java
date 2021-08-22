package com.example.flightsbookingsystem;

import com.example.flightsbookingsystem.models.Admin;
import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Role;
import com.example.flightsbookingsystem.services.AdminService;
import com.example.flightsbookingsystem.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class FlightsBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightsBookingSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AdminService adminService, CustomerService customerService) {
        return args -> {
            customerService.saveCustomer(new Customer(null, "Bahaa", "bahaa1", "123", new ArrayList<>(), new ArrayList<>()));
            customerService.saveCustomer(new Customer(null, "Ahmed", "ahmed8", "789+", new ArrayList<>(), new ArrayList<>()));
            customerService.saveCustomer(new Customer(null, "Hossam", "hossam9", "4562", new ArrayList<>(), new ArrayList<>()));

            adminService.saveRole(new Role(null, "ROLE_ADMIN"));
            adminService.saveRole(new Role(null, "ROLE_CUSTOMER"));

            adminService.saveAdmin(new Admin(null, "Samy", "samy1", "123", new ArrayList<>()));
            adminService.saveAdmin(new Admin(null, "Alaa", "alaa29", "753", new ArrayList<>()));

            adminService.addRoleToAdmin("samy1", "ROLE_ADMIN");
            adminService.addRoleToAdmin("alaa29", "ROLE_ADMIN");

            adminService.addRoleToCustomer("bahaa1","ROLE_CUSTOMER");
            adminService.addRoleToCustomer("ahmed8","ROLE_CUSTOMER");
            adminService.addRoleToCustomer("hossam9","ROLE_CUSTOMER");
        };
    }
}
