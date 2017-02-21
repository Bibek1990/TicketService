package com.walmart.config;

import org.springframework.stereotype.Component;

@Component
public class ConfigPropertyNames {
	private ConfigPropertyNames() {
	}

	// Configurable Properties
	public static final int MAXIMUM_SEAT_CAPACITY = 100;
	public static final int HOLD_SEAT_TIME_IN_SECONDS = 5;
	
	public static final int SEAT_ID_WHEN_EXCEPTION_THROWN = 0;

	// Error Messages
	public static final String CUSTOMER_EMAIL_IS_NOT_VALID_FORMAT = "Your Email is not Valid Format";
	public static final String NUMBER_OF_SEATS_IS_MORE_THAN_MAXIMUM_CAPACITY = "You entered number of seats greater than maximum capacity";
	public static final String NUMBER_OF_SEATS_IS_NOT_AVAILABLE = "We do not have the number of seats available at the moment, try again later!";
	public static final String NUMBER_OF_SEATS_IS_NOT_VALID = "You did not enter a valid number of seats";
	public static final String SEAT_ID_IS_NOT_VALID = "Seat Id is not Valid";
	public static final String SEAT_ID_OR_CUSTOMER_EMAIL_IS_NOT_MATCHING = "Your Seat ID or Customer Email is not matching";

	//Success Message
	public static final String SUCCESSFULLY_REMOVED_ALL = "You successfully removed or deleted everything in memory";
	
	// Pattern
	public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
}
