package com.ozan.flightplanner;

import com.ozan.flightplanner.config.FlightDataConfig;
import com.ozan.flightplanner.config.HolidayConfig;
import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.models.Flight;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Flight ret = new Flight("SJJ", "IST", LocalDate.of(2026, 5, 6),
                LocalTime.of(10, 0), 150);

        when(flightDataConfig.getFlights()).thenReturn(List.of(dep, ret));
        when(holidayConfig.getHolidays()).thenReturn(Set.of(LocalDate.of(2026, 5, 1)));

        FlightRequestDto request = new FlightRequestDto();
        request.setFrom("IST");
        request.setTo("SJJ");
        request.setVacationLength(5);
        request.setMinNonWorkingDays(1);
        request.setStartDate(LocalDate.of(2026, 5, 1));
        request.setEndDate(LocalDate.of(2026, 5, 30));
        request.setMaxResults(10);

        FlightResponseDto response = flightPlanService.planFlight(request);
        assertEquals(1, response.getTrips().size());
    }
}

