package com.flightman.flightmanapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

@RequestMapping("/api")
@RestController
public class BookingController {
        @Autowired
        private BookingService bookingService;

        /* 
         * Method to retrieve bookings present in the database for a userId. 
         * If userId is not supplied, all bookings are returned. 
        */
	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> getBookings(@RequestParam(required = false) UUID userId) {
		try {
                        List<Booking> bookingsList = new ArrayList<Booking>();
                        bookingsList = bookingService.get(userId);
                        if(!bookingsList.isEmpty())
                                return new ResponseEntity<>(bookingsList, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

        /* 
         * Method that creates a new record in the Booking table by associating the supplied userId and flightId. 
         * If failure occurs during booking, returns HTTP NO_CONTENT 
        */
        @PostMapping("/bookings")
	public ResponseEntity<Booking> createBooking(String userId, String flightId, @RequestParam(required = false) String seatNumber, @DateTimeFormat(pattern = "MM-dd-yyyy") Date date) {
		try {
                        Booking booking = this.bookingService.book(userId, flightId, seatNumber, date, true);
                        if (booking != null){
                                return new ResponseEntity<>(booking, HttpStatus.OK);
                        }
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


        @PostMapping("/bookings/id/{id}/usercheckin")
	public ResponseEntity<String> userCheckIn(@PathVariable("id") UUID bookingId) {
		try {
                        String checkedIn = this.bookingService.update(bookingId);
                        return new ResponseEntity<>(checkedIn, HttpStatus.OK);
                        
		} catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
			return new ResponseEntity<>("There was a error with the checkin process!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
