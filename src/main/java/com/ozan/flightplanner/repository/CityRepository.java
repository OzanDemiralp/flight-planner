package com.ozan.flightplanner.repository;

import com.ozan.flightplanner.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCode(String name);

    List<City> findByNameContainingIgnoreCase(String name);
}
