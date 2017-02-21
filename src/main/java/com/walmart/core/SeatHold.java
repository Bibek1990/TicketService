package com.walmart.core;

public class SeatHold {
	private int seatHoldId;
	private long seatHoldTimeStamp;
	private String customerEmail;
	
	public SeatHold(){
		
	}
	
	public SeatHold(int seatHoldId, long seatHoldTimeStamp, String customerEmail) {
		super();
		this.seatHoldId = seatHoldId;
		this.seatHoldTimeStamp = seatHoldTimeStamp;
		this.customerEmail = customerEmail;
	}

	public int getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public long getSeatHoldTimeStamp() {
		return seatHoldTimeStamp;
	}

	public void setSeatHoldTimeStamp(long seatHoldTimeStamp) {
		this.seatHoldTimeStamp = seatHoldTimeStamp;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
