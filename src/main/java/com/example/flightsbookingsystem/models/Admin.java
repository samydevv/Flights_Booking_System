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
@Table(
        name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(name = "admin_user_name_unique",columnNames = "user_name")
        }
)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            name = "user_name",
            nullable = false
    )
    private String userName;
    @Column(
            nullable = false
    )
    private String password;
    @ManyToMany (fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();


}
