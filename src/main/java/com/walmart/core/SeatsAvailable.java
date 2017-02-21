package com.walmart.core;

public class SeatsAvailable {
	private int numSeatsAvailable;
	
	public SeatsAvailable() {
	}

	public SeatsAvailable(int numSeatsAvailable) {
		super();
		this.numSeatsAvailable = numSeatsAvailable;
	}

	public int getNumSeatsAvailable() {
		return numSeatsAvailable;
	}

	public void setNumSeatsAvailable(int numSeatsAvailable) {
		this.numSeatsAvailable = numSeatsAvailable;
	}

	@Override
	public String toString() {
		return "SeatsAvailable [getNumSeatsAvailable()=" + getNumSeatsAvailable() + "]";
	}
}
