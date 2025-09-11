package com.ozan.flightplanner.service;

import com.ozan.flightplanner.entities.CityEntity;
import com.ozan.flightplanner.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityEntity> getAllCities(){
        return cityRepository.findAll();
    }

    public CityEntity getCityByCode(String code){
        return cityRepository.findByCode(code).orElseThrow(() -> new RuntimeException("City not found"));
    }
}
