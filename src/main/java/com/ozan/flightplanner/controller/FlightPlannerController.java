package com.ozan.flightplanner.controller;

import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.entities.CityEntity;
import com.ozan.flightplanner.service.CityService;
import com.ozan.flightplanner.service.FlightPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flightplan")
@CrossOrigin(origins = "http://localhost:3000")
public class FlightPlannerController {
    final private FlightPlanService flightPlanService;
    private final CityService cityService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello FlightPlanner!";
    }

    @PostMapping("/planflight")
    public FlightResponseDto planFlight(@Valid @RequestBody FlightRequestDto request) {
        return flightPlanService.planFlight(request);
    }

    @GetMapping("/cities")
    public List<CityEntity> getCities(){
        return cityService.getAllCities();
    }

    @GetMapping("/cities/search")
    public CityEntity getCityByCode(@RequestParam String code) {
        return cityService.getCityByCode(code);
    }
}
