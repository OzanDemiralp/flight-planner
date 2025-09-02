package com.ozan.flightplanner.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Flight {
    private String from;      // Nereden
    private String to;        // Nereye
    private LocalDate date;   // Uçuş tarihi
    private LocalTime time;   // Uçuş saati
    private double price;     // Fiyat
}

