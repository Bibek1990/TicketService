package com.walmart.validators;

import com.walmart.config.ConfigPropertyNames;
import com.walmart.exception.TicketServiceException;

public class EmailValidator {
	public static boolean validateEmail(String email) throws TicketServiceException{
		if(!email.matches(ConfigPropertyNames.EMAIL_PATTERN)){
			throw new TicketServiceException(ConfigPropertyNames.CUSTOMER_EMAIL_IS_NOT_VALID_FORMAT); 
		} else{
			return true;
		}
	}
}
