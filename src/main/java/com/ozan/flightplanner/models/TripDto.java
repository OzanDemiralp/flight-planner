package com.ozan.flightplanner.models;

import lombok.Data;

@Data
public class TripDto {
    private String departureDate;
    private String departureTime;
    private String returnDate;
    private String returnTime;
    private double departurePrice;
    private double returnPrice;
    private double totalPrice;
}
