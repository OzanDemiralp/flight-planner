package com.ozan.flightplanner.service;

import com.ozan.flightplanner.entities.FlightEntity;
import com.ozan.flightplanner.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    FlightRepository flightRepository;

    public List<FlightEntity> findAll() {
        return flightRepository.findAll();
    }

    public List<FlightEntity> searchFlights(String from, String to){
        return flightRepository.findByFromAndTo(from, to);
    }
}
