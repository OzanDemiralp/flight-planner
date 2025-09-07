package com.ozan.flightplanner;

import com.ozan.flightplanner.dtos.FlightRequestDto;
import com.ozan.flightplanner.validation.StartBeforeEndValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class StartBeforeEndValidatorTest {
    private final StartBeforeEndValidator validator = new StartBeforeEndValidator();
    private final ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

    @Test
    void testValidDates_sameDay() {
        FlightRequestDto dto = new FlightRequestDto();
        dto.setStartDate(LocalDate.of(2025, 9, 7));
        dto.setEndDate(LocalDate.of(2025, 9, 7));
        boolean isValid = validator.isValid(dto, context);
        assertTrue(isValid); // aynı gün geçerli olmalı
    }

    @Test
    void testValidDates_startBeforeEnd(){
        FlightRequestDto dto = new FlightRequestDto();
        dto.setStartDate(LocalDate.of(2025, 9, 7));
        dto.setEndDate(LocalDate.of(2025, 9, 9));
        assertTrue(validator.isValid(dto, context));
    }

    @Test
    void testValidDates_endBeforeStart(){
        FlightRequestDto dto = new FlightRequestDto();
        dto.setStartDate(LocalDate.of(2025, 9, 7));
        dto.setEndDate(LocalDate.of(2025, 9, 5));
        assertFalse(validator.isValid(dto, context));
    }
}
