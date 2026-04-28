package com.amal.sales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, String>> handlestockExpextion(InsufficientStockException ex){
        return new ResponseEntity<>(
                Map.of("messgae", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
