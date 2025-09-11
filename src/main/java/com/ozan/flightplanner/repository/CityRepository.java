package com.ozan.flightplanner.repository;

import com.ozan.flightplanner.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
    Optional<CityEntity> findByCode(String name);

    List<CityEntity> findByNameContainingIgnoreCase(String name);
}
