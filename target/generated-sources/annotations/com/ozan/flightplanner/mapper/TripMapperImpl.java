package com.ozan.flightplanner.mapper;

import com.ozan.flightplanner.models.Flight;
import com.ozan.flightplanner.models.Trip;
import com.ozan.flightplanner.models.TripDto;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-16T13:22:04+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class TripMapperImpl implements TripMapper {

    @Override
    public TripDto toDto(Trip trip) {
        if ( trip == null ) {
            return null;
        }

        TripDto.TripDtoBuilder tripDto = TripDto.builder();

        tripDto.departureDate( tripDepartureDate( trip ) );
        tripDto.departurePrice( trip.getDeparturePrice() );
        tripDto.returnPrice( tripRetPrice( trip ) );
        tripDto.totalPrice( trip.getTotalPrice() );

        tripDto.departureTime( trip.getDeparture().getTime().toString() );
        tripDto.returnDate( trip.getRet().getDate().toString() );
        tripDto.returnTime( trip.getRet().getTime().toString() );

        return tripDto.build();
    }

    private LocalDate tripDepartureDate(Trip trip) {
        Flight departure = trip.getDeparture();
        if ( departure == null ) {
            return null;
        }
        return departure.getDate();
    }

    private double tripRetPrice(Trip trip) {
        Flight ret = trip.getRet();
        if ( ret == null ) {
            return 0.0d;
        }
        return ret.getPrice();
    }
}
