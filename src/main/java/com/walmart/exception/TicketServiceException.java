package com.walmart.exception;

@SuppressWarnings("serial")
public class TicketServiceException extends Exception {
	public TicketServiceException(String message) {
        super(message);
    }
}
