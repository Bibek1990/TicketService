package com.walmart.validators;

import com.walmart.config.ConfigPropertyNames;
import com.walmart.exception.TicketServiceException;

public class NumOfSeatsValidator {
	public static boolean validateNumOfSeatsIsValid(int numOfSeats) throws TicketServiceException{
		if(numOfSeats<=0){
			throw new TicketServiceException(ConfigPropertyNames.NUMBER_OF_SEATS_IS_NOT_VALID); 
		} else if(numOfSeats>ConfigPropertyNames.MAXIMUM_SEAT_CAPACITY){
			throw new TicketServiceException(ConfigPropertyNames.NUMBER_OF_SEATS_IS_MORE_THAN_MAXIMUM_CAPACITY); 
		} else{
			return true;
		}
	}
	
	public static boolean validateNumOfSeatsGreaterThanNumAvailable(int numOfSeats, int numOfSeatsAvailable) throws TicketServiceException{
		if(numOfSeats>numOfSeatsAvailable){
			throw new TicketServiceException(ConfigPropertyNames.NUMBER_OF_SEATS_IS_NOT_AVAILABLE); 
		} else{
			return true;
		}
	}
}
