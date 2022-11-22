package com.flightman.flightmanapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.util.concurrent.TimeUnit;

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

    /* 
     * Method that returns a list of all bookings in the database.
     */
    public List<Booking> get(UUID userId) {
        List<Booking> bookingsList = new ArrayList<Booking>();
        if (userId == null) {
                this.bookingRepository.findAll().forEach(bookingsList::add);
        }
        else {
                User u = this.userRepository.findByUserId(userId);
                this.bookingRepository.findByUser(u).forEach(bookingsList::add);
        }
        return bookingsList;
    }

    /* 
     * Method that creates a record in the booking table of the database 
     * after processing changes on the flight table.
     */
    public Booking book(String userId, String flightId, String seatNumber, Boolean paymentStatus, Boolean useRewardPoints, String date) {
        try {
                User u = this.userRepository.findByUserId(UUID.fromString(userId));
                Flight f = this.flightRepository.findByFlightId(UUID.fromString(flightId));
                Integer availableSeats = f.getNumAvailableSeats();
                int pointsToReturn = f.getCost();

                DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                Date dateObj = formatter.parse(date);

                if(availableSeats > 0) {
                        if (useRewardPoints) {
                            if (u.getRewardsMiles() < pointsToReturn) {
                                return null;
                            }

                            u.setRewardsMiles(u.getRewardsMiles() - pointsToReturn);
                        }
                        else {
                            u.setRewardsMiles(u.getRewardsMiles() + pointsToReturn / 10);
                        }

                        this.userRepository.save(u);
                        Booking booking = new Booking(u, f, seatNumber, true, useRewardPoints, dateObj);
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

    public Boolean deleteBooking(String bookingID, String userID) {
        Booking booking = this.bookingRepository.findByBookingId(UUID.fromString(bookingID));

        if (UUID.fromString(userID) != booking.getUser().getID()) {
            return false;
        }

        this.bookingRepository.deleteById(UUID.fromString(bookingID));
        updateRewardPointsForBookingDeletion(booking.getUser().getID(), booking.getFlight().getCost(), booking.getDate());

        return true;
    }

    public Boolean updateRewardPointsForBookingDeletion(UUID id, int numPointsUsed, Date flightDate) {
        Date currentDate = new Date();

        if ((currentDate.compareTo(flightDate) >= 0) || (numPointsUsed == 0)) {
            return true;
        }

        int timeDiff = (int) Math.abs(flightDate.getTime() - currentDate.getTime());
        int daysDiff = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        int pointsToReturn = numPointsUsed;

        if (daysDiff < 7) {
            pointsToReturn /= 2;
        }

        User user = this.userRepository.findByUserId(id);

        user.setRewardsMiles(user.getRewardsMiles() + pointsToReturn);

        return this.userRepository.save(user) != null ? true : false;
    }
}
