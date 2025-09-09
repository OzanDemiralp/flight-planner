package com.ozan.flightplanner.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="cities")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
}
