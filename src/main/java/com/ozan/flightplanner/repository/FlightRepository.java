package com.ozan.flightplanner.repository;

import com.ozan.flightplanner.entities.CityEntity;
import com.ozan.flightplanner.entities.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {

    List<FlightEntity> findByFromAndTo(String from, String to);
    List<FlightEntity> findByFromAndToAndDateLessThanEqual(String from, String to, LocalDate endDate);
    List<FlightEntity> findByFromAndToAndDateGreaterThanEqual(String from, String to, LocalDate startDate);
}
