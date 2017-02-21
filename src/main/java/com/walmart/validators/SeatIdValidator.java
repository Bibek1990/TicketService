package com.walmart.validators;

import com.walmart.config.ConfigPropertyNames;
import com.walmart.exception.TicketServiceException;

public class SeatIdValidator {
	public static boolean validateSeatHoldId(int seatHoldId) throws TicketServiceException{
		if(seatHoldId<=0){
			throw new TicketServiceException(ConfigPropertyNames.SEAT_ID_IS_NOT_VALID); 
		} else{
			return true;
		}
	}
}
