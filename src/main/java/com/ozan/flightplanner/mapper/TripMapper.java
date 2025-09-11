package com.ozan.flightplanner.mapper;

import com.ozan.flightplanner.models.Trip;
import com.ozan.flightplanner.models.TripDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TripMapper {

    // Map Trip → TripDto
    @Mapping(target = "departureDate", source = "departure.date")
    @Mapping(target = "departureTime", expression = "java(trip.getDeparture().getTime().toString())")
    @Mapping(target = "returnDate", expression = "java(trip.getRet().getDate().toString())")
    @Mapping(target = "returnTime", expression = "java(trip.getRet().getTime().toString())")
    @Mapping(target = "departurePrice", source = "departurePrice")
    @Mapping(target = "returnPrice", source = "ret.price")
    @Mapping(target = "totalPrice", source = "totalPrice")
    TripDto toDto(Trip trip);
}
