package com.ozan.flightplanner.service;

import com.ozan.flightplanner.entities.City;
import com.ozan.flightplanner.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    public City getCityByCode(String code){
        return cityRepository.findByCode(code).orElseThrow(() -> new RuntimeException("City not found"));
    }
}
