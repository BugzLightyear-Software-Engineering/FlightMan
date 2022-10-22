package com.flightman.flightmanapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.repositories.BookingRepository;
import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.repositories.FlightRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Booking> get(UUID userId) {
        List<Booking> bookingsList = new ArrayList<Booking>();
        if (userId == null) {
                this.bookingRepository.findAll().forEach(bookingsList::add);
        }
        // TODO - Get specific user's bookings
        // } else {
        //         Optional<User> u = this.UserRepository.findByUserId(userId);
        //         this.BookingRepository.findByUser(u).forEach(bookingsList::add);
        // }
        return bookingsList;
    }

    public Booking book(String userId, String flightId, String seatNumber, Boolean paymentStatus) {
        try {
                User u = this.userRepository.findByUserId(UUID.fromString(userId));
                Flight f = this.flightRepository.findByFlightId(UUID.fromString(flightId));
                Integer availableSeats = f.getNumAvailableSeats();
                if(availableSeats > 0){
                        Booking booking = new Booking(u, f, seatNumber, true);
                        booking = this.bookingRepository.save(booking);
                        f.setNumAvailableSeats(availableSeats-1);
                        this.flightRepository.save(f);
                        return booking;
                }
                return null;
        } catch (Exception e) {
                System.out.println("Error while booking!");
                e.printStackTrace(new java.io.PrintStream(System.out));
                return null;
        }
    }
}
