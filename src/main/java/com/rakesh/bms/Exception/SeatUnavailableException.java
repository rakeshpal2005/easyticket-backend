package com.rakesh.bms.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SeatUnavailableException extends RuntimeException{

    public SeatUnavailableException(String message){

        super(message);
    }


}

