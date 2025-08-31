package com.ozan.flightplanner.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trip {
    private Flight departure;
    private Flight ret;
    private double departurePrice;
    private double returnPrice;
    private double totalPrice;
}
