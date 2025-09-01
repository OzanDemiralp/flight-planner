package com.ozan.flightplanner.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Configuration
@ConfigurationProperties(prefix = "holiday")
public class HolidayConfig {
    private List<String > dates;

    public Set<LocalDate> getHolidays(){
        return dates.stream().map(LocalDate::parse).collect(Collectors.toSet());
    }
}
