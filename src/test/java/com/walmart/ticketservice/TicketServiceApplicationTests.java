package com.walmart.ticketservice;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.walmart.config.ConfigPropertyNames;
import com.walmart.core.HoldSeatInfo;
import com.walmart.core.ReserveSeatInfo;
import com.walmart.core.SeatHold;
import com.walmart.core.SeatsAvailable;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketServiceApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	private static final String SERVER_PORT = "8080";
	private static final String URL_PREFIX = "http://localhost:" + SERVER_PORT;
	private static final String NUM_SEATS_AVAILABLE_URL = URL_PREFIX + "/numSeatsAvailable";
	private static final String HOLD_SEATS_URL = URL_PREFIX + "/hold";
	private static final String RESERVE_SEATS_URL = URL_PREFIX + "/reserve";
	private static final String REMOVE_ALL_SEATS_URL = URL_PREFIX + "/removeAll";

	private static final int numOfSeatsToHold = 5;
	private static final int ZERO = 0;
	private static final int NEGATIVE_FOUR = -4;
	private static final String VALID_EMAIL = "abc@gmail.com";
	private static final String INVALID_EMAIL = "abcdefg12";
	private static final String VALID_EMAIL_FOR_RESERVING = "xyz@hotmail.com";
	private static final String VALID_BUT_WRONG_EMAIL_FOR_RESERVING = "xyz@yahoo.com";

	@Before
	public void beforeRemoveAll() {
		String response = restTemplate.getForObject(REMOVE_ALL_SEATS_URL, String.class);
		assertEquals(response, ConfigPropertyNames.SUCCESSFULLY_REMOVED_ALL);
	}

	@Test
	public void testNumSeatsAvailable() {
		int numberOfSeatsAvailable = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(ConfigPropertyNames.MAXIMUM_SEAT_CAPACITY, numberOfSeatsAvailable);
	}

	//Baseline Test
	@Test
	public void testFindAndHoldSeats() {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, VALID_EMAIL), SeatHold.class);
		int numberOfSeatsAvailableAfterHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold - numberOfSeatsAvailableAfterHold, numOfSeatsToHold);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(seatHoldResponseEntity.getBody().getCustomerEmail(), VALID_EMAIL);
	}

	//Number of Seats Greater than available seats
	@Test
	public void testFindAndHoldSeatsWithNumberOfSeatsGreaterThanAvailableSeats() {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numberOfSeatsAvailableBeforeHold + 1, VALID_EMAIL), SeatHold.class);
		int numberOfSeatsAvailableAfterHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold, numberOfSeatsAvailableAfterHold);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

		if (numberOfSeatsAvailableBeforeHold == ConfigPropertyNames.MAXIMUM_SEAT_CAPACITY) {
			assertEquals(seatHoldResponseEntity.getBody().getCustomerEmail(),
					ConfigPropertyNames.NUMBER_OF_SEATS_IS_MORE_THAN_MAXIMUM_CAPACITY);
		} else {
			assertEquals(seatHoldResponseEntity.getBody().getCustomerEmail(),
					ConfigPropertyNames.NUMBER_OF_SEATS_IS_NOT_AVAILABLE);
		}
	}

	//Number of Seats Greater than maximum capacity
	@Test
	public void testFindAndHoldSeatsWithNumberOfSeatsGreaterThanMaximumCapacity() {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(ConfigPropertyNames.MAXIMUM_SEAT_CAPACITY + 1, VALID_EMAIL), SeatHold.class);
		int numberOfSeatsAvailableAfterHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold, numberOfSeatsAvailableAfterHold);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(seatHoldResponseEntity.getBody().getCustomerEmail(),
				ConfigPropertyNames.NUMBER_OF_SEATS_IS_MORE_THAN_MAXIMUM_CAPACITY);
	}

	//Number of Seats is not valid or it is 0 or less
	@Test
	public void testFindAndHoldSeatsWithNumberOfSeatsZero() {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(ZERO, VALID_EMAIL), SeatHold.class);
		int numberOfSeatsAvailableAfterHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold, numberOfSeatsAvailableAfterHold);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(seatHoldResponseEntity.getBody().getCustomerEmail(),
				ConfigPropertyNames.NUMBER_OF_SEATS_IS_NOT_VALID);
	}

	//Number of Seats is not valid or it is 0 or less
	@Test
	public void testFindAndHoldSeatsWithNumberOfSeatsNegative() {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(NEGATIVE_FOUR, VALID_EMAIL), SeatHold.class);
		int numberOfSeatsAvailableAfterHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold, numberOfSeatsAvailableAfterHold);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(seatHoldResponseEntity.getBody().getCustomerEmail(),
				ConfigPropertyNames.NUMBER_OF_SEATS_IS_NOT_VALID);
	}

	//Put invalid email-Empty
	@Test
	public void testFindAndHoldSeatsWithEmptyEmail() {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, ""), SeatHold.class);
		int numberOfSeatsAvailableAfterHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold, numberOfSeatsAvailableAfterHold);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(seatHoldResponseEntity.getBody().getCustomerEmail(),
				ConfigPropertyNames.CUSTOMER_EMAIL_IS_NOT_VALID_FORMAT);
	}

	//Put invalid email-Not Empty but just text
	@Test
	public void testFindAndHoldSeatsWithInvalidEmail() {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, INVALID_EMAIL), SeatHold.class);
		int numberOfSeatsAvailableAfterHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold, numberOfSeatsAvailableAfterHold);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(seatHoldResponseEntity.getBody().getCustomerEmail(),
				ConfigPropertyNames.CUSTOMER_EMAIL_IS_NOT_VALID_FORMAT);
	}

	//Check whether the hold seats expire after wait period
	@Test
	public void testFindAndHoldSeatsWhetherSeatsExpire() throws InterruptedException {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		restTemplate.postForEntity(HOLD_SEATS_URL, new HoldSeatInfo(numOfSeatsToHold, INVALID_EMAIL), SeatHold.class);
		TimeUnit.SECONDS.sleep(ConfigPropertyNames.HOLD_SEAT_TIME_IN_SECONDS + 1);
		int numberOfSeatsAvailableAfterHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold, numberOfSeatsAvailableAfterHold);
	}

	//Reserve Seats Baseline
	@Test
	public void testReserveSeats() {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, VALID_EMAIL_FOR_RESERVING), SeatHold.class);
		ResponseEntity<String> reserveSeatsResponse = restTemplate.postForEntity(RESERVE_SEATS_URL,
				new ReserveSeatInfo(seatHoldResponseEntity.getBody().getSeatHoldId(),
						seatHoldResponseEntity.getBody().getCustomerEmail()),
				String.class);
		int numberOfSeatsAvailableAfterReserve = restTemplate
				.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class).getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold - numOfSeatsToHold, numberOfSeatsAvailableAfterReserve);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(reserveSeatsResponse.getStatusCode(), HttpStatus.OK);
	}

	//Reserve Seats after time expires
	@Test
	public void testReserveSeatsWithSeatHoldIdsExpire() throws InterruptedException {
		int numberOfSeatsAvailableBeforeHold = restTemplate.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class)
				.getNumSeatsAvailable();
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, VALID_EMAIL_FOR_RESERVING), SeatHold.class);
		TimeUnit.SECONDS.sleep(ConfigPropertyNames.HOLD_SEAT_TIME_IN_SECONDS + 1);
		ResponseEntity<String> reserveSeatsResponse = restTemplate.postForEntity(RESERVE_SEATS_URL,
				new ReserveSeatInfo(seatHoldResponseEntity.getBody().getSeatHoldId(),
						seatHoldResponseEntity.getBody().getCustomerEmail()),
				String.class);
		int numberOfSeatsAvailableAfterReserve = restTemplate
				.getForObject(NUM_SEATS_AVAILABLE_URL, SeatsAvailable.class).getNumSeatsAvailable();
		assertEquals(numberOfSeatsAvailableBeforeHold, numberOfSeatsAvailableAfterReserve);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(reserveSeatsResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(reserveSeatsResponse.getBody(), ConfigPropertyNames.SEAT_ID_OR_CUSTOMER_EMAIL_IS_NOT_MATCHING);
	}

	//SeatId is invalid or it is 0 or less
	@Test
	public void testReserveSeatsWithSeatHoldIdInvalidWithZero() {
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, VALID_EMAIL_FOR_RESERVING), SeatHold.class);
		ResponseEntity<String> reserveSeatsResponse = restTemplate.postForEntity(RESERVE_SEATS_URL,
				new ReserveSeatInfo(ZERO, seatHoldResponseEntity.getBody().getCustomerEmail()), String.class);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(reserveSeatsResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(reserveSeatsResponse.getBody(), ConfigPropertyNames.SEAT_ID_IS_NOT_VALID);
	}

	//SeatId is invalid or it is 0 or less
	@Test
	public void testReserveSeatsWithSeatHoldIdInvalidWithNegativeNumber() {
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, VALID_EMAIL_FOR_RESERVING), SeatHold.class);
		ResponseEntity<String> reserveSeatsResponse = restTemplate.postForEntity(RESERVE_SEATS_URL,
				new ReserveSeatInfo(NEGATIVE_FOUR, seatHoldResponseEntity.getBody().getCustomerEmail()), String.class);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(reserveSeatsResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(reserveSeatsResponse.getBody(), ConfigPropertyNames.SEAT_ID_IS_NOT_VALID);
	}

	//Correct Format but wrong email
	@Test
	public void testReserveSeatsWithEmailNotMatching() {
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, VALID_EMAIL_FOR_RESERVING), SeatHold.class);
		ResponseEntity<String> reserveSeatsResponse = restTemplate.postForEntity(RESERVE_SEATS_URL, new ReserveSeatInfo(
				seatHoldResponseEntity.getBody().getSeatHoldId(), VALID_BUT_WRONG_EMAIL_FOR_RESERVING), String.class);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(reserveSeatsResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(reserveSeatsResponse.getBody(), ConfigPropertyNames.SEAT_ID_OR_CUSTOMER_EMAIL_IS_NOT_MATCHING);
	}

	//Correct Email but Incorrect SeatHoldId
	@Test
	public void testReserveSeatsWithEmailEmpty() {
		ResponseEntity<SeatHold> seatHoldResponseEntity = restTemplate.postForEntity(HOLD_SEATS_URL,
				new HoldSeatInfo(numOfSeatsToHold, VALID_EMAIL_FOR_RESERVING), SeatHold.class);
		ResponseEntity<String> reserveSeatsResponse = restTemplate.postForEntity(RESERVE_SEATS_URL,
				new ReserveSeatInfo(ConfigPropertyNames.MAXIMUM_SEAT_CAPACITY + 1,
						seatHoldResponseEntity.getBody().getCustomerEmail()),
				String.class);
		assertEquals(seatHoldResponseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(reserveSeatsResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(reserveSeatsResponse.getBody(), ConfigPropertyNames.SEAT_ID_OR_CUSTOMER_EMAIL_IS_NOT_MATCHING);
	}
}