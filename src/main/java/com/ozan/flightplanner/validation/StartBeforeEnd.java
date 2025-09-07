package com.ozan.flightplanner.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StartBeforeEndValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartBeforeEnd {
    String message() default "Start date must be before end date or the same.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
