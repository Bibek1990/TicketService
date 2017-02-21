package com.walmart.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.config.ConfigPropertyNames;
import com.walmart.core.HoldSeatInfo;
import com.walmart.core.ReserveSeatInfo;
import com.walmart.core.SeatHold;
import com.walmart.core.SeatsAvailable;
import com.walmart.datastorage.TicketServiceMapCollection;
import com.walmart.exception.TicketServiceException;
import com.walmart.ticketservice.TicketServiceImpl;
import com.walmart.validators.EmailValidator;
import com.walmart.validators.NumOfSeatsValidator;
import com.walmart.validators.SeatIdValidator;

@RestController
public class TicketServiceController {
	@Autowired
	TicketServiceImpl ticketServiceImpl;

	@RequestMapping("/numSeatsAvailable")
	public SeatsAvailable numSeatsAvailable() {
		return (new SeatsAvailable(ticketServiceImpl.numSeatsAvailable()));
	}
	
	@RequestMapping(value = "/hold")
    public ResponseEntity<SeatHold> findAndHoldSeats(@RequestBody HoldSeatInfo holdSeatInfo) {
		try{
			EmailValidator.validateEmail(holdSeatInfo.getCustomerEmail());
			NumOfSeatsValidator.validateNumOfSeatsIsValid(holdSeatInfo.getNumSeats());
			NumOfSeatsValidator.validateNumOfSeatsGreaterThanNumAvailable(holdSeatInfo.getNumSeats(), TicketServiceMapCollection.getNumSeatAvailableMap().size());
		} catch(TicketServiceException ticketServiceException){
			return new ResponseEntity<SeatHold>(new SeatHold(ConfigPropertyNames.SEAT_ID_WHEN_EXCEPTION_THROWN,ConfigPropertyNames.SEAT_ID_WHEN_EXCEPTION_THROWN,ticketServiceException.getMessage()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<SeatHold>(ticketServiceImpl.findAndHoldSeats(holdSeatInfo.getNumSeats(), holdSeatInfo.getCustomerEmail()), HttpStatus.OK);
	}

	@RequestMapping(value = "/reserve")
    public ResponseEntity<String> reserveSeats(@RequestBody ReserveSeatInfo reserveSeatInfo) {
		try{
			EmailValidator.validateEmail(reserveSeatInfo.getCustomerEmail());
			SeatIdValidator.validateSeatHoldId(reserveSeatInfo.getSeatHoldId());
		} catch(TicketServiceException ticketServiceException){
			return new ResponseEntity<String>(ticketServiceException.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		String response=ticketServiceImpl.reserveSeats(reserveSeatInfo.getSeatHoldId(), reserveSeatInfo.getCustomerEmail());
		
		if(!response.equals(ConfigPropertyNames.SEAT_ID_OR_CUSTOMER_EMAIL_IS_NOT_MATCHING)){
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ConfigPropertyNames.SEAT_ID_OR_CUSTOMER_EMAIL_IS_NOT_MATCHING, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/removeAll")
	public ResponseEntity<String> removeAll() {
		TicketServiceMapCollection.getNumSeatAvailableMap().clear();
		TicketServiceMapCollection.getNumSeatHoldMap().clear();
		TicketServiceMapCollection.getNumSeatReserveMap().clear();
		
		int availableResources=ConfigPropertyNames.MAXIMUM_SEAT_CAPACITY;
		Map<Integer, SeatHold> numSeatAvailableMap = TicketServiceMapCollection.getNumSeatAvailableMap();
		for (int i = 1; i <= availableResources; i++) {
			numSeatAvailableMap.put(i, new SeatHold());
		}
		return new ResponseEntity<String>(ConfigPropertyNames.SUCCESSFULLY_REMOVED_ALL, HttpStatus.OK);
	}
	
	@RequestMapping("/getAllAvailable")
	public Map<Integer, SeatHold> getAllAvailable() {
		return TicketServiceMapCollection.getNumSeatAvailableMap();
	}

	@RequestMapping("/getAllReserve")
	public Map<Integer, SeatHold> getAllReserve() {
		return TicketServiceMapCollection.getNumSeatReserveMap();
	}

	@RequestMapping("/getAllHold")
	public Map<Integer, SeatHold> getAllHold() {
		return TicketServiceMapCollection.getNumSeatHoldMap();
	}
}
