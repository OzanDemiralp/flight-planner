package com.ozan.flightplanner.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "flights")
@Data
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_city")
    String from;

    @Column(name = "to_city")
            
    String to;
    LocalDate date;
    LocalTime time;
    int price;
}
