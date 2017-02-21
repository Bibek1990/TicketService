package com.walmart.core;

public class ReserveSeatInfo {
	private int seatHoldId;
	private String customerEmail;
	
	public ReserveSeatInfo(){
		
	}
	
	public ReserveSeatInfo(int seatHoldId, String customerEmail) {
		super();
		this.seatHoldId = seatHoldId;
		this.customerEmail = customerEmail;
	}
	
	public int getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
}
