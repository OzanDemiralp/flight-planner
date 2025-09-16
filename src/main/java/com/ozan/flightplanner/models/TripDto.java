package com.ozan.flightplanner.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TripDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    private String departureTime;
    private String returnDate;
    private String returnTime;
    private double departurePrice;
    private double returnPrice;
    private double totalPrice;
}
