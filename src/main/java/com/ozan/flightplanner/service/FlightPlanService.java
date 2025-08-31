package com.ozan.flightplanner.service;

import com.ozan.flightplanner.config.FlightDataConfig;
import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.dtos.FlightResponseDto;
import com.ozan.flightplanner.models.Flight;
import com.ozan.flightplanner.models.Trip;
import com.ozan.flightplanner.models.TripDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightPlanService {
    private final FlightDataConfig flightDataConfig;

    public FlightResponseDto planFlight(FlightRequestDto request) {
        List<Flight> flights = flightDataConfig.getFlights();
        String from = request.getFrom();
        String to = request.getTo();
        int duration = request.getVacationLength();

        // 1) Dönüş uçuşlarını tarihe göre grupla: Map<LocalDate, List<Flight>>
        Map<LocalDate, List<Flight>> returnsByDate = flights.stream()
                .filter(f -> f.getFrom().equalsIgnoreCase(to) && f.getTo().equalsIgnoreCase(from))
                .collect(Collectors.groupingBy(Flight::getDate));

        // 2) Tüm gidişleri işle
        List<Trip> results = new ArrayList<>();
        for (Flight dep : flights) {
            if (!dep.getFrom().equalsIgnoreCase(from) || !dep.getTo().equalsIgnoreCase(to)) {
                continue; // sadece IST->SJJ gibi gidişleri işle
            }
            LocalDate desiredReturnDate = dep.getDate().plusDays(duration);
            List<Flight> candidateReturns = returnsByDate.getOrDefault(desiredReturnDate, Collections.emptyList());
            for (Flight ret : candidateReturns) {
                double total = dep.getPrice() + ret.getPrice();
                results.add(new Trip(dep, ret, dep.getPrice(), ret.getPrice(), total));
            }
        }

        // 3) En ucuzdan pahalıya sırala
        results.sort(Comparator.comparingDouble(Trip::getTotalPrice));

        // 4) Top-N al
        List<Trip> top = results.stream().limit(request.getMaxResults()).toList();

        // 5) Trip -> TripDto dönüşümü
        List<TripDto> dtoList = top.stream().map(trip -> {
            TripDto dto = new TripDto();
            dto.setDepartureDate(trip.getDeparture().getDate().toString());
            dto.setDepartureTime(trip.getDeparture().getTime().toString());
            dto.setReturnDate(trip.getRet().getDate().toString());
            dto.setReturnTime(trip.getRet().getTime().toString());
            dto.setDeparturePrice(trip.getDeparturePrice());
            dto.setReturnPrice(trip.getRet().getPrice());
            dto.setTotalPrice(trip.getTotalPrice());
            return dto;
        }).toList();
        return FlightResponseDto.builder().trips(dtoList).build();
    }
}