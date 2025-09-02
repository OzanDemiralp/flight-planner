package com.ozan.flightplanner.service;

import com.ozan.flightplanner.models.Trip;
import com.ozan.flightplanner.models.TripDto;


public class TripDtoFactory {
    public static TripDto fromTrip(Trip trip) {
        TripDto dto = new TripDto();
        dto.setDepartureDate(trip.getDeparture().getDate());
        dto.setDepartureTime(trip.getDeparture().getTime().toString());
        dto.setReturnDate(trip.getRet().getDate().toString());
        dto.setReturnTime(trip.getRet().getTime().toString());
        dto.setDeparturePrice(trip.getDeparturePrice());
        dto.setReturnPrice(trip.getRet().getPrice());
        dto.setTotalPrice(trip.getTotalPrice());
        return dto;
    }
}
