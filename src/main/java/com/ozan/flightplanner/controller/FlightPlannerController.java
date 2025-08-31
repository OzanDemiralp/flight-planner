package com.ozan.flightplanner.controller;

import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.service.FlightPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flightplan")
public class FlightPlannerController {
    final private FlightPlanService flightPlanService;
    @GetMapping("/hello")
    public String hello() {
        return "Hello FlightPlanner!";
    }
    @PostMapping("/planflight")
    public FlightResponseDto planFlight(@RequestBody FlightRequestDto request) {
        return flightPlanService.planFlight(request);
    }
}
