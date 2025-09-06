package com.ozan.flightplanner.controller;

import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.service.FlightPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flightplan")
@CrossOrigin(origins = "http://localhost:3000") // ya da "*"
public class FlightPlannerController {
    final private FlightPlanService flightPlanService;
    @GetMapping("/hello")
    public String hello() {
        return "Hello FlightPlanner!";
    }
    @PostMapping("/planflight")
    public FlightResponseDto planFlight(@Valid @RequestBody FlightRequestDto request) {
        return flightPlanService.planFlight(request);
    }
}
