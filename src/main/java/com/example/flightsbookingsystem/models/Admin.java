package com.example.flightsbookingsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity // table in database
@Data  // for getters ans setters
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;
    private String name;
    private String userName;
    private String password;
    @ManyToMany (fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();


}
