package com.ozan.flightplanner.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlightRequestDto {
    @NotNull
    private String from;

    @NotNull
    private String to;
//    private String startDate;
//    private String endDate;
    @Min(1)
    private int vacationLength;

    @Min(0)
    private int minNonWorkingDays;

    @Min(1)
    private int maxResults;
}
