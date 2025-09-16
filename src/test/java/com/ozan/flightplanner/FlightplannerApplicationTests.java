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

	@Test
	void testCountNonWorkingDays(){
		LocalDate start = LocalDate.of(2026, 5, 1);
		int duration = 7;
		int count = flightPlanService.countNonWorkingDays(start, duration);
		assertEquals(3, count);
	}

}
