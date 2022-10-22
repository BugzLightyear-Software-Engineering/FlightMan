package com.flightman.flightmanapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.services.BookingService;

@RestController
public class BookingController {
        @Autowired
        private BookingService bookingService;

	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> getBookings(@RequestParam(required = false) UUID userId) {
		try {
                        List<Booking> bookingsList = new ArrayList<Booking>();
                        bookingsList = bookingService.get(userId);
                        if(!bookingsList.isEmpty())
                                return new ResponseEntity<>(bookingsList, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

        @PostMapping("/bookings")
	public ResponseEntity<Booking> createBooking(String userId, String flightId, String seatNumber) {
		try {
                        Booking booking = this.bookingService.book(userId, flightId, seatNumber, true);
                        if (booking != null){
                                return new ResponseEntity<>(booking, HttpStatus.OK);
                        }
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
