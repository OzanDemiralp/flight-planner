package com.ozan.flightplanner;

import com.ozan.flightplanner.config.FlightDataConfig;
import com.ozan.flightplanner.config.HolidayConfig;
import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.models.Flight;
import com.ozan.flightplanner.models.TripDto;
import com.ozan.flightplanner.service.FlightPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FlightPlanServiceTest {

    @Mock
    private FlightDataConfig flightDataConfig;

    @Mock
    private HolidayConfig holidayConfig;

    @InjectMocks
    private FlightPlanService flightPlanService;

    @Test
    void testPlanFlightWithMockData() {
        Flight dep = new Flight("IST", "SJJ", LocalDate.of(2026, 5, 1),
                LocalTime.of(8, 0), 100);
        Flight ret = new Flight("BEL", "IST", LocalDate.of(2026, 5, 6),
                LocalTime.of(7, 0), 150);

        when(flightDataConfig.getFlights()).thenReturn(List.of(dep, ret));
        when(holidayConfig.getHolidays()).thenReturn(Set.of(LocalDate.of(2026, 5, 1)));

        FlightRequestDto request = new FlightRequestDto();
        request.setDepartureFrom("IST");
        request.setDepartureTo("SJJ");
        request.setReturnFrom("BEL");
        request.setReturnTo("IST");
        request.setVacationLength(5);
        request.setMinNonWorkingDays(3);
        request.setStartDate(LocalDate.of(2026, 5, 1));
        request.setEndDate(LocalDate.of(2026, 5, 30));
        request.setMaxResults(10);

        FlightResponseDto response = flightPlanService.planFlight(request);
        assertEquals(1, response.getTrips().size());
    }

    @Test
    void shouldReturnEmptyTrips_whenNoFlightsAvailable(){
        when(flightDataConfig.getFlights()).thenReturn(List.of());

        FlightRequestDto request = new FlightRequestDto();
        request.setDepartureFrom("IST");
        request.setDepartureTo("SJJ");
        request.setReturnFrom("SJJ");    // <-- yeni alan
        request.setReturnTo("IST");      // <-- yeni alan
        request.setVacationLength(5);
        request.setStartDate(LocalDate.of(2026, 5, 1));
        request.setEndDate(LocalDate.of(2026, 5, 30));
        request.setMaxResults(10);

        FlightResponseDto response = flightPlanService.planFlight(request);
        assertEquals(0, response.getTrips().size());
    }

    @Test
    void shouldReturnEmptyTrips_whenReturnFlightDoesNotMatchVacationLength  (){
        Flight dep = new Flight("IST", "SJJ", LocalDate.of(2026, 5, 1),
                LocalTime.of(8, 0), 100);
        Flight ret = new Flight("SJJ", "IST", LocalDate.of(2026, 5, 8),
                LocalTime.of(8, 0), 100);

        when(flightDataConfig.getFlights()).thenReturn(List.of(dep, ret));

        FlightRequestDto request = new FlightRequestDto();
        request.setDepartureFrom("IST");
        request.setDepartureTo("SJJ");
        request.setReturnFrom("SJJ");    // <-- yeni alan
        request.setReturnTo("IST");      // <-- yeni alan
        request.setVacationLength(5);
        request.setStartDate(LocalDate.of(2026, 5, 1));
        request.setEndDate(LocalDate.of(2026, 5, 30));
        request.setMaxResults(10);

        FlightResponseDto response = flightPlanService.planFlight(request);

        assertEquals(0, response.getTrips().size());
    }

    @Test
    void shouldReturnEmptyTrips_whenNonWorkingDaysNotEnough(){
        Flight dep = new Flight("IST", "SJJ", LocalDate.of(2026, 5, 1),
                LocalTime.of(8, 0), 100);
        Flight ret = new Flight("SJJ", "IST", LocalDate.of(2026, 5, 6),
                LocalTime.of(8, 0), 100);


        when(flightDataConfig.getFlights()).thenReturn(List.of(dep, ret));
        when(holidayConfig.getHolidays()).thenReturn(Set.of());

        FlightRequestDto request = new FlightRequestDto();
        request.setDepartureFrom("IST");
        request.setDepartureTo("SJJ");
        request.setReturnFrom("SJJ");    // <-- yeni alan
        request.setReturnTo("IST");      // <-- yeni alan
        request.setVacationLength(5);
        request.setStartDate(LocalDate.of(2026, 5, 1));
        request.setEndDate(LocalDate.of(2026, 5, 30));
        request.setMinNonWorkingDays(3);
        request.setMaxResults(10);

        FlightResponseDto response = flightPlanService.planFlight(request);

        assertEquals(0, response.getTrips().size());
    }

    @Test
    void shouldReturnEmptyTrips_whenReturnFlightOutOfDateInterval(){
        Flight dep = new Flight("IST", "SJJ", LocalDate.of(2026, 5, 1),
                LocalTime.of(8, 0), 100);
        Flight ret = new Flight("SJJ", "IST", LocalDate.of(2026, 5, 6),
                LocalTime.of(8, 0), 100);


        when(flightDataConfig.getFlights()).thenReturn(List.of(dep, ret));

        FlightRequestDto request = new FlightRequestDto();
        request.setDepartureFrom("IST");
        request.setDepartureTo("SJJ");
        request.setReturnFrom("SJJ");    // <-- yeni alan
        request.setReturnTo("IST");      // <-- yeni alan
        request.setVacationLength(5);
        request.setStartDate(LocalDate.of(2026, 5, 1));
        request.setEndDate(LocalDate.of(2026, 5, 4));
        request.setMinNonWorkingDays(2);
        request.setMaxResults(10);

        FlightResponseDto response = flightPlanService.planFlight(request);

        assertEquals(0, response.getTrips().size());
    }

    @Test
    void shouldReturnCorrectTrips_whenVacationLengthOneDay(){
        Flight dep = new Flight("IST", "SJJ", LocalDate.of(2026, 5, 1),
                LocalTime.of(8, 0), 100);
        Flight ret = new Flight("SJJ", "IST", LocalDate.of(2026, 5, 2),
                LocalTime.of(8, 0), 100);

        when(flightDataConfig.getFlights()).thenReturn(List.of(dep, ret));

        FlightRequestDto request = new FlightRequestDto();
        request.setDepartureFrom("IST");
        request.setDepartureTo("SJJ");
        request.setReturnFrom("SJJ");    // <-- yeni alan
        request.setReturnTo("IST");      // <-- yeni alan
        request.setVacationLength(1);
        request.setStartDate(LocalDate.of(2026, 5, 1));
        request.setEndDate(LocalDate.of(2026, 5, 30));
        request.setMinNonWorkingDays(0);
        request.setMaxResults(10);

        FlightResponseDto response = flightPlanService.planFlight(request);

        assertEquals(1, response.getTrips().size());
    }

    @Test
    void shouldReturnCorrectReturn_whenOneReturnExistsBeforeDeparture(){
        Flight dep = new Flight("IST", "SJJ", LocalDate.of(2026, 5, 2),
                LocalTime.of(7, 0), 100);
        Flight ret = new Flight("SJJ", "IST", LocalDate.of(2026, 5, 2),
                LocalTime.of(1, 0), 100);
        Flight ret2 = new Flight("SJJ", "IST", LocalDate.of(2026, 5, 2),
                LocalTime.of(23, 0), 100);

        when(flightDataConfig.getFlights()).thenReturn(List.of(dep, ret, ret2));

        FlightRequestDto request = new FlightRequestDto();
        request.setDepartureFrom("IST");
        request.setDepartureTo("SJJ");
        request.setReturnFrom("SJJ");    // <-- yeni alan
        request.setReturnTo("IST");      // <-- yeni alan
        request.setVacationLength(0);
        request.setStartDate(LocalDate.of(2026, 5, 1));
        request.setEndDate(LocalDate.of(2026, 5, 30));
        request.setMinNonWorkingDays(0);
        request.setMaxResults(10);

        FlightResponseDto response = flightPlanService.planFlight(request);

        TripDto trip = response.getTrips().get(0);
        LocalTime depTime = LocalTime.parse(trip.getDepartureTime());
        LocalTime retTime = LocalTime.parse(trip.getReturnTime());

        assertEquals(LocalTime.of(23, 0), retTime, "Dönüş uçuşu beklenen uçuş olmalı");
        assertTrue(depTime.isBefore(retTime), "Dönüş uçuşu gidişten sonra olmalı");
    }
}
