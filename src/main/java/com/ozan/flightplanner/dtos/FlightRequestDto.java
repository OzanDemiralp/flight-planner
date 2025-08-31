package com.ozan.flightplanner.dtos;

import lombok.Data;

@Data
public class FlightRequestDto {
    private String from;
    private String to;
//    private String startDate;
//    private String endDate;
    private int vacationLength;
    private int maxResults;//ileride kullanılır
}
