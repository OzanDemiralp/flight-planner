package com.ozan.flightplanner.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
public class FlightRequestDto {
    @NotNull
    private String from;

    @NotNull
    private String to;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate startDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate endDate;

    @Min(1)
    private int vacationLength;

    @Min(0)
    private int minNonWorkingDays;

    @Min(1)
    private int maxResults;
}
