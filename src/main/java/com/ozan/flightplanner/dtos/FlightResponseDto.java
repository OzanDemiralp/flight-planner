package com.ozan.flightplanner.dtos;

import com.ozan.flightplanner.models.TripDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlightResponseDto {
    private List<TripDto> trips;
}
