package com.example.flightsbookingsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity // table in database
@Data  // for getters ans setters
@NoArgsConstructor
@AllArgsConstructor
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
    private  String fromPlace;
    @Column(
            nullable = false
    )
    private  String toPlace;
    @Column(
            nullable = false
    )
    private  Date date;

}
