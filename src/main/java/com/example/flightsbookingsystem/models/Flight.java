package com.example.flightsbookingsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long  id;
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
    @JsonFormat(pattern="dd-MM-yyyy")
    private  Date date;

}
