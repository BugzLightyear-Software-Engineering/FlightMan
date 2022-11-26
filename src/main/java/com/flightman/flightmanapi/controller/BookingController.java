package com.flightman.flightmanapi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.services.BookingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api")
@RestController
public class BookingController {
        @Autowired
        private BookingService bookingService;

        /*
         * Method to retrieve bookings present in the database for a userId.
         * If userId is not supplied, all bookings are returned.
         */
        @ApiOperation(value = "Get Bookings", notes = "Takes in the user ID and returns bookings (if any)")
        @ApiResponses({ @ApiResponse(code = 200, message = "Booking is successfully returned. If there are no bookings, an empty list is returned."),
                        @ApiResponse(code = 500, message = "There was an unexpected problem while returning bookings") })
        @GetMapping("/bookings")
        public ResponseEntity<List<Booking>> getBookings(@RequestParam(required = false) UUID userId) {
                try {
                        List<Booking> bookingsList = new ArrayList<Booking>();
                        bookingsList = bookingService.get(userId);
                        return new ResponseEntity<>(bookingsList, HttpStatus.OK);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        /*
         * Method that creates a new record in the Booking table by associating the
         * supplied userId and flightId.
         * If failure occurs during booking, returns HTTP NO_CONTENT
         */
        @ApiOperation(value = "Create Bookings", notes = "Takes in the user ID, flight ID, seat number, and the date of the flight. It books the flight if seats are available and returns the booking details.")
        @ApiResponses({ @ApiResponse(code = 201, message = "The created booking is successfully returned. If there are no bookings, an empty list is returned."),
                        @ApiResponse(code = 500, message = "There was an unexpected problem while creating bookings") })
        @PostMapping("/bookings")
        public ResponseEntity<?> createBooking(String userId, String flightId,
                        @RequestParam(required = false) String seatNumber, String date) {
                Date d;
                if (!this.bookingService.validateUser(userId)) {
                        return new ResponseEntity<>("Invalid User ID", HttpStatus.BAD_REQUEST);
                }
                if (!this.bookingService.validateFlight(flightId)) {
                        return new ResponseEntity<>("Invalid Flight ID", HttpStatus.BAD_REQUEST);
                }
                try {
                        SimpleDateFormat DateFor = new SimpleDateFormat("MM-dd-yyyy");
                        d = DateFor.parse(date);
                        date = DateFor.format(d);
                        if (d.before(new Date())) {
                                return new ResponseEntity<>("Invalid Date", HttpStatus.BAD_REQUEST);
                        }
                } catch (Exception e) {
                        return new ResponseEntity<>("Invalid Date", HttpStatus.BAD_REQUEST);
                }
                try {
                        Booking booking = this.bookingService.book(userId, flightId, seatNumber, date, true);
                        if (booking != null) {
                                return new ResponseEntity<>(booking, HttpStatus.CREATED);
                        }
                        return new ResponseEntity<>("Could not create booking(s)", HttpStatus.INTERNAL_SERVER_ERROR);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @PostMapping("/bookings/id/{id}/usercheckin")
        public ResponseEntity<String> userCheckIn(@PathVariable("id") UUID bookingId) {
                try {
                        String checkedIn = this.bookingService.checkInUser(bookingId);
                        return new ResponseEntity<>(checkedIn, HttpStatus.OK);

                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>("There was a error with the checkin process!",
                                        HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @PostMapping("/bookings/id/{id}/luggagecheckin")
        public ResponseEntity<?> luggageCheckIn(@PathVariable("id") String bookingId,
                        @RequestParam(required = true) Integer count,
                        @RequestParam(required = true) float totalWeight) {
                if (bookingId == null || bookingId == "" || !this.bookingService.validateBooking(bookingId)) {
                        return new ResponseEntity<>("Invalid Booking ID", HttpStatus.BAD_REQUEST);
                }
                if (!this.bookingService.validateCheckInTime(bookingId)) {
                        return new ResponseEntity<>("Check in is only allowed two hours before flight departure",
                                        HttpStatus.BAD_REQUEST);
                }
                if (this.bookingService.getLuggageCheckInStatus(bookingId)) {
                        return new ResponseEntity<>("Luggage has been already checked in!",
                                        HttpStatus.BAD_REQUEST);
                }
                if (count < 0 || count > 2) {
                        return new ResponseEntity<>("Only 0 to 2 luggages are allowed",
                                        HttpStatus.BAD_REQUEST);
                }
                if (totalWeight < 0 || totalWeight > 46) {
                        return new ResponseEntity<>("Both luggages can weigh only upto 46 kgs",
                                        HttpStatus.BAD_REQUEST);
                }
                try {
                        if (this.bookingService.checkInLuggage(bookingId, count, totalWeight)) {
                                return new ResponseEntity<>("Luggage checked In Successfully!", HttpStatus.OK);
                        }
                        return new ResponseEntity<>("Unable to check in luggage!", HttpStatus.OK);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>("There was a error with the luggage checkin process!",
                                        HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}
