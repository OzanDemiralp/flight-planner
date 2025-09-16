package com.ozan.flightplanner.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ozan.flightplanner.validation.StartBeforeEnd;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@StartBeforeEnd
public class FlightRequestDto {

    @NotNull
    private String departureFrom;

    @NotNull
    private String departureTo;

    @NotNull
    private String returnFrom;

    @NotNull
    private String returnTo;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate startDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate endDate;

    @Min(0)
    private int vacationLength;

    @Min(0)
    private int minNonWorkingDays;
}
