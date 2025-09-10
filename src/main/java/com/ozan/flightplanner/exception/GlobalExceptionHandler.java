package com.ozan.flightplanner.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        // Validation hataları (DTO constraint violations)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidationExceptions(
                MethodArgumentNotValidException ex,
                HttpServletRequest request) {

            Map<String, String> fieldErrors = new HashMap<>();
            for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                fieldErrors.put(error.getField(), error.getDefaultMessage());
            }
            for (var error : ex.getBindingResult().getGlobalErrors()) {
                fieldErrors.put(error.getObjectName(), error.getDefaultMessage());
            }

            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.BAD_REQUEST.value());
            body.put("error", "Bad Request");
            body.put("message", "Validation failed");
            body.put("path", request.getRequestURI());
            body.put("fieldErrors", fieldErrors);

            return ResponseEntity.badRequest().body(body);
        }

        // Diğer beklenmeyen exception’lar
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> handleAllOtherExceptions(
                Exception ex,
                HttpServletRequest request) {

            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            body.put("error", "Internal Server Error");
            body.put("message", ex.getMessage());
            body.put("path", request.getRequestURI());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
