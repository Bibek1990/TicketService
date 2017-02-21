package com.walmart.ticketservice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.walmart.config.ConfigPropertyNames;
import com.walmart.core.SeatHold;
import com.walmart.datastorage.TicketServiceMapCollection;

@Component
public class TicketServiceImpl implements TicketService {
	private final AtomicInteger seatId = new AtomicInteger();

	@Override
	public int numSeatsAvailable() {
		long holdTime = ConfigPropertyNames.HOLD_SEAT_TIME_IN_SECONDS;

		List<Integer> keyListToRemoveFromHoldMap = new ArrayList<Integer>();

		TicketServiceMapCollection.getNumSeatHoldMap().forEach((seatNumId, seatHoldObj) -> {
			if (Instant.now().getEpochSecond() - seatHoldObj.getSeatHoldTimeStamp() > holdTime) {
				TicketServiceMapCollection.getNumSeatAvailableMap().put(seatNumId, new SeatHold());
				keyListToRemoveFromHoldMap.add(seatNumId);
			}
		});

		keyListToRemoveFromHoldMap.forEach(item -> {
			TicketServiceMapCollection.getNumSeatHoldMap().remove(item);
		});

		return TicketServiceMapCollection.getNumSeatAvailableMap().size();
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		int numSeatsAvailable = numSeatsAvailable();
		if (numSeatsAvailable >= numSeats) {
			int index = 1;
			List<Integer> keyListToRemoveFromAvailableMap = new ArrayList<Integer>();
			Map<Integer, SeatHold> numSeatAvailableMap = TicketServiceMapCollection.getNumSeatAvailableMap();
			SeatHold seatHold = new SeatHold(seatId.incrementAndGet(), Instant.now().getEpochSecond(), customerEmail);

			for (Map.Entry<Integer, SeatHold> entry : numSeatAvailableMap.entrySet()) {
				if (index > numSeats) {
					break;
				} else {
					// Add to Seat Hold Map with the same key
					TicketServiceMapCollection.getNumSeatHoldMap().put(entry.getKey(), seatHold);

					// Add to temporary list to remove from Available Map
					keyListToRemoveFromAvailableMap.add(entry.getKey());
					index++;
				}
			}

			keyListToRemoveFromAvailableMap.forEach(item -> {
				numSeatAvailableMap.remove(item);
			});

			return seatHold;
		}
		return new SeatHold();
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		numSeatsAvailable();
		List<Integer> keyListToRemoveFromHoldMap = new ArrayList<Integer>();

		TicketServiceMapCollection.getNumSeatHoldMap().forEach((seatNumId, seatHoldObj) -> {
			if (seatHoldObj.getSeatHoldId() == seatHoldId && seatHoldObj.getCustomerEmail().equals(customerEmail)) {
				TicketServiceMapCollection.getNumSeatReserveMap().put(seatNumId, seatHoldObj);
				keyListToRemoveFromHoldMap.add(seatNumId);
			}
		});

		keyListToRemoveFromHoldMap.forEach(item -> {
			TicketServiceMapCollection.getNumSeatHoldMap().remove(item);
		});

		if (!keyListToRemoveFromHoldMap.isEmpty()) {
			return UUID.randomUUID().toString();
		}

		return ConfigPropertyNames.SEAT_ID_OR_CUSTOMER_EMAIL_IS_NOT_MATCHING;
	}

}
