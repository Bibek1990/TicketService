Ticket Booking Service:

Implements a simple ticket service that facilitates the discovery, temporary hold and final reservation of seats within a high-demand performance venue.

Library Used for Development:
1)	Spring Boot 
2)	Spring REST Template
3)	Spring REST
4)	Java 1.8
5)	Maven

Assumptions:
1)	Every seat is assigned a SeatId
2)	There is an overall maximum number of seats which is configurable
3)	There is an overall maximum hold time when a user reserves a seat, which is configurable
4)	The hold seats are not automatically replenished to available section until a request comes through


To Run the program:
1)	Cd into the particular directory where project is
2)	Run mvnw spring-boot:run
3)	The above command will run spring-boot in localhost port 8080 by default

To Test the program:
You can use Postman or Curl

http://localhost:8080/numSeatsAvailable

Sample Response:
100

http://localhost:8080/hold

Sample Request Body:

{
  "numSeats": 10,
  "customerEmail": "sariph1@gmail.com"
}

Sample Response Body:
{
  "seatHoldId": 1,
  "seatHoldTimeStamp": 1487657339,
  "customerEmail": "sariph1@gmail.com"
}

http://localhost:8080/reserve

Sample Request Body:
{
  "seatHoldId":1,
  "customerEmail": "sariph1@gmail.com"
}

Sample Response Body:

a2267cfc-076f-42d8-a8e1-0a697c47e9d4


To Run the test cases:
Cd into the particular directory where project is (in a different terminal than one that you are running the program)
Run mvn test
[Please make sure you use a different terminal than one where you running spring boot because the test cases will run in random port while calling the server]

How Program was developed:
Three different ConcurrentHashMap is used
1)	Map of Hold Seats
2)	Map of Number of Available Seats
3)	Map of All the Reserved Seats

Every time client request for Hold or Reserve or Number of Available Seats the program will go 
through the Hold Seats Maps and see if any of the seat has expired and moves them to Number of Available Seats

