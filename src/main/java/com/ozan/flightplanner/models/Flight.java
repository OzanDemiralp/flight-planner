package com.ozan.flightplanner.models;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Flight {
    private String from;      // Nereden
    private String to;        // Nereye
    private LocalDate date;   // Uçuş tarihi
    private LocalTime time;   // Uçuş saati
    private double price;     // Fiyat
}

