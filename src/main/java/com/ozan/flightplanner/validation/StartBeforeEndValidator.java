package com.ozan.flightplanner.validation;

import com.ozan.flightplanner.dtos.FlightRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, FlightRequestDto> {

    @Override
    public boolean isValid(FlightRequestDto dto, ConstraintValidatorContext context) {
        if(dto.getStartDate() == null || dto.getEndDate() == null){
            return true;
        }
        return dto.getStartDate().isBefore(dto.getEndDate())
                || dto.getStartDate().isEqual(dto.getEndDate());
    }
}
