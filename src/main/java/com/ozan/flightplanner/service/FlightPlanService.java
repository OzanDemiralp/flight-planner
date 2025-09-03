package com.ozan.flightplanner.service;

import com.ozan.flightplanner.config.FlightDataConfig;
import com.ozan.flightplanner.config.HolidayConfig;
import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.models.Flight;
import com.ozan.flightplanner.models.Trip;
import com.ozan.flightplanner.models.TripDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class FlightPlanService {
    private final FlightDataConfig flightDataConfig;
    private final HolidayConfig holidayConfig;

    public FlightResponseDto planFlight(FlightRequestDto request) {

        List<Flight> flights = flightDataConfig.getFlights();
        String from = request.getFrom();
        String to = request.getTo();
        int duration = request.getVacationLength();

        // 1) Dönüş uçuşlarını tarihe göre grupla: Map<LocalDate, List<Flight>>
        Map<LocalDate, List<Flight>> returnsByDate = flights.stream()
                .filter(f -> f.getFrom().equalsIgnoreCase(to)
                        && f.getTo().equalsIgnoreCase(from)
                        && !f.getDate().isAfter(request.getEndDate()))
                .collect(Collectors.groupingBy(Flight::getDate));

        // 2) Tüm gidişleri işle
        List<Trip> results = new ArrayList<>();
        for (Flight dep : flights) {
            if (!dep.getFrom().equalsIgnoreCase(from) || !dep.getTo().equalsIgnoreCase(to)) continue; // sadece IST->SJJ gibi gidişleri işle
            if (dep.getDate().isBefore(request.getStartDate())) continue;

            LocalDate desiredReturnDate = dep.getDate().plusDays(duration);
            List<Flight> candidateReturns = returnsByDate.getOrDefault(desiredReturnDate, Collections.emptyList());
            for (Flight ret : candidateReturns) {
                double total = dep.getPrice() + ret.getPrice();
                int nonWorkingDays = countNonWorkingDays(dep.getDate(), duration);
                if (nonWorkingDays < request.getMinNonWorkingDays()) continue; // minimum altındakileri hiç ekleme
                if(dep.getDate().isEqual(ret.getDate())) if(dep.getTime().isAfter(ret.getTime())) continue;
                results.add(new Trip(dep, ret, dep.getPrice(), ret.getPrice(), total, nonWorkingDays));
            }
        }

        // 3) En ucuzdan pahalıya sırala
        results.sort(Comparator.comparingInt(Trip::getNonWorkingDays).reversed()
                .thenComparingDouble(Trip::getTotalPrice));

        // 4) Top-N al
        List<Trip> top = results.stream().limit(request.getMaxResults()).toList();

        // 5) Trip -> TripDto dönüşümü
        List<TripDto> dtoList = top.stream().map(TripDtoFactory::fromTrip).toList();
        return FlightResponseDto.builder().trips(dtoList).build();
    }

    public int countNonWorkingDays(LocalDate start, int duration) {
        Set<LocalDate> holidays = holidayConfig.getHolidays();

        return (int)
                IntStream.range(0, duration)
                        .mapToObj(start::plusDays)
                        .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                                || date.getDayOfWeek() == DayOfWeek.SUNDAY
                                || holidays.contains(date))
                        .count();
    }


}