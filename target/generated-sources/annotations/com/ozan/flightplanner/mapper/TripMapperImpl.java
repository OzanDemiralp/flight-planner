package com.ozan.flightplanner.mapper;

import com.ozan.flightplanner.models.Flight;
import com.ozan.flightplanner.models.Trip;
import com.ozan.flightplanner.models.TripDto;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-11T22:04:23+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class TripMapperImpl implements TripMapper {

    @Override
    public TripDto toDto(Trip trip) {
        if ( trip == null ) {
            return null;
        }

        TripDto tripDto = new TripDto();

        tripDto.setDepartureDate( tripDepartureDate( trip ) );
        tripDto.setDeparturePrice( trip.getDeparturePrice() );
        tripDto.setReturnPrice( tripRetPrice( trip ) );
        tripDto.setTotalPrice( trip.getTotalPrice() );

        tripDto.setDepartureTime( trip.getDeparture().getTime().toString() );
        tripDto.setReturnDate( trip.getRet().getDate().toString() );
        tripDto.setReturnTime( trip.getRet().getTime().toString() );

        return tripDto;
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
