package com.ozan.flightplanner;

import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.service.FlightPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FlightplannerApplicationTests {
	@Autowired
	FlightPlanService flightPlanService;

	// Unit Test
	@Test
	void testCountNonWorkingDays(){
		LocalDate start = LocalDate.of(2026, 5, 1);
		int duration = 7;
		int count = flightPlanService.countNonWorkingDays(start, duration);
		assertEquals(3, count);
	}

	// Regression Test
	@Test
	void testPlanFlightDateRange(){
		FlightRequestDto request = new FlightRequestDto();
		request.setFrom("IST");
		request.setTo("SJJ");
		request.setStartDate(LocalDate.of(2026, 5, 1));
		request.setEndDate(LocalDate.of(2026, 5, 30));
		request.setVacationLength(5);
		request.setMinNonWorkingDays(3);
		request.setMaxResults(100);

		var response = flightPlanService.planFlight(request);

		// Tüm dönüşler startDate ve endDate arasında mı kontrol et
		response.getTrips().forEach(trip ->
				assertTrue(!trip.getDepartureDate().isBefore(request.getStartDate())
						&& !trip.getDepartureDate().isAfter(request.getEndDate()))
		);

	}
}
