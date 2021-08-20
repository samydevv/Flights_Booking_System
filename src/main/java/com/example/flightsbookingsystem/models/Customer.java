package com.example.flightsbookingsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity //used for Spring JPA
@Data  // for getters ans setters
@NoArgsConstructor //Lombok, create no args constructor
@AllArgsConstructor //Lombok annotation, creates all args constructor
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "customer_user_name_unique",columnNames = "user_name")
        }
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id ;
    @Column(
            nullable = false
    )
    private  String name;
    @Column(
            name = "user_name",
            nullable = false
    )
    private  String userName;
    @Column(
            nullable = false
    )
    private  String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Flight> flights = new ArrayList<>();


}
