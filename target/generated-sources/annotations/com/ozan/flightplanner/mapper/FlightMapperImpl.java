package com.ozan.flightplanner.mapper;

import com.ozan.flightplanner.entities.FlightEntity;
import com.ozan.flightplanner.models.Flight;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-16T13:22:04+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class FlightMapperImpl implements FlightMapper {

    @Override
    public Flight toFlight(FlightEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Flight flight = new Flight();

        flight.setFrom( entity.getFrom() );
        flight.setTo( entity.getTo() );
        flight.setDate( entity.getDate() );
        flight.setTime( entity.getTime() );
        flight.setPrice( entity.getPrice() );

        return flight;
    }

    @Override
    public FlightEntity toEntity(Flight dto) {
        if ( dto == null ) {
            return null;
        }

        FlightEntity flightEntity = new FlightEntity();

        flightEntity.setFrom( dto.getFrom() );
        flightEntity.setTo( dto.getTo() );
        flightEntity.setDate( dto.getDate() );
        flightEntity.setTime( dto.getTime() );
        flightEntity.setPrice( (int) dto.getPrice() );

        return flightEntity;
    }
}
