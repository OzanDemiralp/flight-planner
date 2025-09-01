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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FlightPlannerTest {

    @Autowired
    private FlightPlanService flightPlanService;

 }

