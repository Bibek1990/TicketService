package com.walmart.ticketservice;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.walmart.config.ConfigPropertyNames;
import com.walmart.core.SeatHold;
import com.walmart.datastorage.TicketServiceMapCollection;

@SpringBootApplication(scanBasePackages = { "com.walmart.*" })
public class TicketServiceApplication {
	public static void main(String[] args) {
		initializeResourceAvailableMap();
		SpringApplication.run(TicketServiceApplication.class, args);
	}

	private static void initializeResourceAvailableMap() {
		int availableResources = ConfigPropertyNames.MAXIMUM_SEAT_CAPACITY;
		Map<Integer, SeatHold> numSeatAvailableMap = TicketServiceMapCollection.getNumSeatAvailableMap();
		for (int i = 1; i <= availableResources; i++) {
			numSeatAvailableMap.put(i, new SeatHold());
		}
	}
}