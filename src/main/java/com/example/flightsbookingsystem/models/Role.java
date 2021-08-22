package com.example.flightsbookingsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity //used for Spring JPA
@Data  // for getters ans setters
@NoArgsConstructor //Lombok, create no args constructor
@AllArgsConstructor //Lombok annotation, creates all args constructor
@Table(
        name = "role"
)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(
            nullable = false
    )
    private String name;

}
