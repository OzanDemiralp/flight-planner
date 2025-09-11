package com.ozan.flightplanner.mapper;

import com.ozan.flightplanner.entities.FlightEntity;
import com.ozan.flightplanner.models.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    Flight toFlight(FlightEntity entity);
    FlightEntity toEntity(Flight dto);
}
