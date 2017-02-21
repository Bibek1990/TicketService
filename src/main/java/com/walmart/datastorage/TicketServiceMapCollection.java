package com.walmart.datastorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.walmart.core.SeatHold;

public class TicketServiceMapCollection {
	private static Map<Integer, SeatHold> numSeatHoldMap = new ConcurrentHashMap<Integer,SeatHold>();
	private static Map<Integer, SeatHold> numSeatReserveMap = new ConcurrentHashMap<Integer,SeatHold>();
	private static Map<Integer, SeatHold> numSeatAvailableMap = new ConcurrentHashMap<Integer,SeatHold>();
	
	public static Map<Integer, SeatHold> getNumSeatHoldMap() {
		return numSeatHoldMap;
	}
	public static void setNumSeatHoldMap(Map<Integer, SeatHold> numSeatHoldMap) {
		TicketServiceMapCollection.numSeatHoldMap = numSeatHoldMap;
	}
	public static Map<Integer, SeatHold> getNumSeatReserveMap() {
		return numSeatReserveMap;
	}
	public static void setNumSeatReserveMap(Map<Integer, SeatHold> numSeatReserveMap) {
		TicketServiceMapCollection.numSeatReserveMap = numSeatReserveMap;
	}
	public static Map<Integer, SeatHold> getNumSeatAvailableMap() {
		return numSeatAvailableMap;
	}
	public static void setNumSeatAvailableMap(Map<Integer, SeatHold> numSeatAvailableMap) {
		TicketServiceMapCollection.numSeatAvailableMap = numSeatAvailableMap;
	}
}
