package com.walmart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TicketServiceExceptionHandler {

	@ExceptionHandler({TicketServiceException.class})
    public ResponseEntity<String> handleSeatReservationNotValidException(TicketServiceException ticketServiceException) {
        return new ResponseEntity<String>(ticketServiceException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
