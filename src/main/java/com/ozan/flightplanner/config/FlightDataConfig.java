package com.ozan.flightplanner.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ozan.flightplanner.models.Flight;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Configuration
public class FlightDataConfig {

    @Getter
    private List<Flight> flights;

    @Value("${dummy.file.path}")
    private String dummyFilePath;

    @PostConstruct
    public void loadDummyData() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (InputStream is = getClass().getResourceAsStream("/" + dummyFilePath)) {
            flights = Arrays.asList(mapper.readValue(is, Flight[].class));
            System.out.println("Flights loaded: " + flights.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
