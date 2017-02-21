package com.walmart.core;

public class HoldSeatInfo {
	private int numSeats;
	private String customerEmail;
	
	public HoldSeatInfo(){
	};
	
	public HoldSeatInfo(int numSeats, String customerEmail) {
		super();
		this.numSeats = numSeats;
		this.customerEmail = customerEmail;
	}
	
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
