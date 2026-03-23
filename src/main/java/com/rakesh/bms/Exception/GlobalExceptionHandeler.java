package com.rakesh.bms.Exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.Date;
@ControllerAdvice
public class GlobalExceptionHandeler {

    // 1. Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse errorDetails = new ErrorResponse(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // 2. Seat Unavailable
    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<?> seatUnavailableException(SeatUnavailableException ex, WebRequest request) {

        ErrorResponse errorDetails = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // 3. Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 4. Database Error
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDatabaseException(DataIntegrityViolationException ex, WebRequest request) {

        ErrorResponse errorDetails = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Database Error",
                "Invalid or missing data",
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // 5. Runtime Exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {

        ErrorResponse errorDetails = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Error",
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // 6. Global Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionalHandler(Exception ex, WebRequest request) {

        ErrorResponse errorDetails = new ErrorResponse(
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Server Error",
                "Something went wrong",
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}