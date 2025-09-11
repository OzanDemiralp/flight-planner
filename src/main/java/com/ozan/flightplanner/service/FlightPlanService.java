package com.ozan.flightplanner.service;

import com.ozan.flightplanner.config.HolidayConfig;
import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.mapper.FlightMapper;
import com.ozan.flightplanner.mapper.TripMapper;
import com.ozan.flightplanner.models.Flight;
import com.ozan.flightplanner.models.Trip;
import com.ozan.flightplanner.models.TripDto;
import com.ozan.flightplanner.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FlightPlanService {
    private final HolidayConfig holidayConfig;
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final TripMapper tripMapper;

    public FlightPlanService(HolidayConfig holidayConfig, FlightRepository flightRepository, FlightMapper flightMapper, TripMapper tripMapper) {
        this.holidayConfig = holidayConfig;
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.tripMapper = tripMapper;
    }

    public FlightResponseDto planFlight(FlightRequestDto request) {

        String departureFrom = request.getDepartureFrom();
        String departureTo = request.getDepartureTo();
        String retFrom = request.getReturnFrom();
        String retTo = request.getReturnTo();

        // 1) Dönüş uçuşlarını DB’den çek ve tarih bazında grupla
        Map<LocalDate, List<Flight>> returnsByDate = flightRepository
                .findByFromAndToAndDateLessThanEqual(retFrom, retTo, request.getEndDate())
                .stream()
                .map(flightMapper::toFlight)
                .collect(Collectors.groupingBy(Flight::getDate));

        // 2) Gidiş uçuşlarını DB’den çek
        List<Flight> departureFlights = flightRepository
                .findByFromAndToAndDateGreaterThanEqual(departureFrom, departureTo, request.getStartDate())
                .stream()
                .map(flightMapper::toFlight)
                .toList();

        // 3) Tüm gidişleri işle
        List<Trip> results = new ArrayList<>();
        for (Flight dep : departureFlights) {

            LocalDate desiredReturnDate = dep.getDate().plusDays(request.getVacationLength());
            List<Flight> candidateReturns = returnsByDate.getOrDefault(desiredReturnDate, Collections.emptyList());

            for (Flight ret : candidateReturns) {
                double total = dep.getPrice() + ret.getPrice();
                int nonWorkingDays = countNonWorkingDays(dep.getDate(), request.getVacationLength());

                if (nonWorkingDays < request.getMinNonWorkingDays()) continue;
                if (dep.getDate().isEqual(ret.getDate()) && dep.getTime().isAfter(ret.getTime())) continue;

                results.add(new Trip(dep, ret, dep.getPrice(), ret.getPrice(), total, nonWorkingDays));
            }
        }

        // 4) En ucuzdan pahalıya sırala
        results.sort(Comparator.comparingInt(Trip::getNonWorkingDays).reversed()
                .thenComparingDouble(Trip::getTotalPrice));

        // 5) Trip -> TripDto dönüşümü
        List<TripDto> dtoList = results.stream()
                .map(tripMapper::toDto)
                .toList();

        return FlightResponseDto.builder().trips(dtoList).build();
    }

    public int countNonWorkingDays(LocalDate start, int duration) {
        Set<LocalDate> holidays = holidayConfig.getHolidays();

        return (int) IntStream.range(0, duration)
                .mapToObj(start::plusDays)
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                        || date.getDayOfWeek() == DayOfWeek.SUNDAY
                        || holidays.contains(date))
                .count();
    }
}
