package com.ozan.flightplanner;

import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.models.TripDto;
import com.ozan.flightplanner.service.FlightPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FlightPlannerTest {

    @Autowired
    private FlightPlanService flightPlanService;


    @Test
    void testPlanFlightIncludesFullWeekend(){
        FlightRequestDto flightRequestDto = new FlightRequestDto();
        flightRequestDto.setFrom("IST");
        flightRequestDto.setTo("SJJ");
        flightRequestDto.setVacationLength(4);
        flightRequestDto.setMaxResults(200);

        FlightResponseDto flightResponseDto = flightPlanService.planFlight(flightRequestDto);

        List<TripDto> trips = flightResponseDto.getTrips();

        for (TripDto trip : trips) {
            LocalDate depDate = LocalDate.parse(trip.getDepartureDate());
            LocalDate retDate = LocalDate.parse(trip.getReturnDate());

            boolean hasSaturday = false;
            boolean hasSunday = false;

            for (LocalDate d = depDate; !d.isAfter(retDate); d = d.plusDays(1)) {
                if (d.getDayOfWeek() == DayOfWeek.SATURDAY) hasSaturday = true;
                if (d.getDayOfWeek() == DayOfWeek.SUNDAY) hasSunday = true;
            }

            assertTrue(hasSaturday && hasSunday,
                    "Trip from " + depDate + " to " + retDate + " does not include full weekend");
        }

        System.out.println("All trips include a full weekend. Test passed!");


    }
}
