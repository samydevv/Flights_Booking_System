package com.example.flightsbookingsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity //used for Spring JPA
@Data  // for getters ans setters
@NoArgsConstructor //Lombok, create no args constructor
@AllArgsConstructor //Lombok annotation, creates all args constructor
@Table(
        name = "flight"
)
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long  id;
    @Column(
            nullable = false
    )
    private long fare ;
    @Column(
            nullable = false
    )
    private String classType ;
    @Column(
            nullable = false
    )
    private  String departurePlace;
    @Column(
            nullable = false
    )
    private  String arrivePlace;
    @Column(
            nullable = false
    )
    private  Date date;

}
