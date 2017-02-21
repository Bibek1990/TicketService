<b>Ticket Booking Service:</b>


Implements a simple ticket service that facilitates the discovery, temporary hold and final reservation of seats within a high-demand performance venue.


<br>


<b>Library Used for Development:</b>

1)	Spring Boot 

2)	Spring REST Template

3)	Spring REST

4)	Java 1.8

5)	Maven
<br>



<b>Assumptions:</b>

1)	Every seat is assigned a SeatId

2)	There is an overall maximum number of seats which is configurable

3)	There is an overall maximum hold time when a user reserves a seat, which is configurable

4)	The hold seats are not automatically replenished to available section until a request comes through

<br>



<b>To Run the program:</b>

1)	Cd into the particular directory where project is

2)	Run mvnw spring-boot:run

3)	The above command will run spring-boot in localhost port 8080 by default

<br>


<b>To Test the program:</b>
You can use Postman or Curl
<br>


<>http://localhost:8080/numSeatsAvailable</b>
<br>
<b>Sample Response:</b>
<br>
100
<br>

<b>http://localhost:8080/hold/></b>
<br>
<b>Sample Request Body:</b>
<br>
<br>
{
  "numSeats": 10,
  "customerEmail": "sariph1@gmail.com"
}
<br>
<br>
<br>
<b>Sample Response Body:</b>
<br>
<br>
{
  "seatHoldId": 1,
  "seatHoldTimeStamp": 1487657339,
  "customerEmail": "sariph1@gmail.com"
}
<br>
<br>
<br>
<b>http://localhost:8080/reserve</b>
<br>
<br>
<b>Sample Request Body:</b>
<br>
<br>
{
  "seatHoldId":1,
  "customerEmail": "sariph1@gmail.com"
}
<br>
<br>
<br>
<b>Sample Response Body:</b>
<br>
<br>
a2267cfc-076f-42d8-a8e1-0a697c47e9d4
<br>
<br>
<br>
<br>
<b>To Run the test cases:</b>
<br>
Cd into the particular directory where project is (in a different terminal than one that you are running the program)
<br>
Run mvn test
<br>
[Please make sure you use a different terminal than one where you running spring boot because the test cases will run in random port while calling the server]
<br>
<br>
<b>How Program was developed:</b>
<br>
<b>Three different ConcurrentHashMap is used</b>
<br>
1)	Map of Hold Seats
<br>
2)	Map of Number of Available Seats
<br>
3)	Map of All the Reserved Seats
<br>
<br>
Every time client request for Hold or Reserve or Number of Available Seats the program will go 
through the Hold Seats Maps and see if any of the seat has expired and moves them to Number of Available Seats
<br>
<br>
