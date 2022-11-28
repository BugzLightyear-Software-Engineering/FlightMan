package com.flightman.flightmanapi.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.Luggage;
import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.repositories.BookingRepository;
import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.repositories.FlightRepository;
import com.flightman.flightmanapi.repositories.LuggageRepository;

@Service
public class BookingService {

        @Autowired
        private BookingRepository bookingRepository;

        @Autowired
        private FlightRepository flightRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private LuggageRepository luggageRepository;

        private static final Logger logger = LogManager.getLogger(BookingService.class);

        /*
         * Method that returns a list of all bookings in the database.
         */
        public List<Booking> get(UUID userId) {
                List<Booking> bookingsList = new ArrayList<Booking>();
                if (userId == null) {
                        this.bookingRepository.findAll().forEach(bookingsList::add);
                } else {
                        User u = this.userRepository.findByUserId(userId);
                        this.bookingRepository.findByUser(u).forEach(bookingsList::add);
                }
                return bookingsList;
        }

        /*
         * Method that validates if user exists
         */
        public Boolean validateUser(String userId) {
                User u = this.userRepository.findByUserId(UUID.fromString(userId));
                if (u != null) {
                        return true;
                }
                return false;
        }

        /*
         * Method that validates if flight exists
         */
        public Boolean validateFlight(String flightId) {
                Flight f = this.flightRepository.findByFlightId(UUID.fromString(flightId));
                if (f != null) {
                        return true;
                }
                return false;
        }

        /*
         * Method that validates if booking exists
         */
        public Boolean validateBooking(String bookingId) {
                try {
                        Booking b = this.bookingRepository.findByBookingId(UUID.fromString(bookingId));
                        if (b != null) {
                                return true;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return false;
        }

        /*
         * Method that validates if check in is allowed based on time to flight
         * departure
         */
        public Boolean validateCheckInTime(String bookingId) {
                try {
                        Booking b = this.bookingRepository.findByBookingId(UUID.fromString(bookingId));
                        if (getTimeToFlightDeparture(b) >= -2) {
                                return true;
                        }
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                return false;
        }

        /*
         * Method that returns an available seat on a flight on a specific day.
         */
        public String generateSeatNumber(Flight f, Date d) {
                String[] possibleSeatList = new String[] { "1A", "1B", "1C", "1D", "1E", "1F", "2A", "2B", "2C", "2D",
                                "2E", "2F", "3A", "3B", "3C", "3D", "3E", "3F", "4A", "4B", "4C", "4D", "4E", "4F",
                                "5A", "5B", "5C", "5D", "5E", "5F", "6A", "6B", "6C", "6D", "6E", "6F", "7A", "7B",
                                "7C", "7D", "7E", "7F", "8A", "8B", "8C", "8D", "8E", "8F", "9A", "9B", "9C", "9D",
                                "9E", "9F", "10A", "10B", "10C", "10D", "10E", "10F", "11A", "11B", "11C", "11D", "11E",
                                "11F", "12A", "12B", "12C", "12D", "12E", "12F", "13A", "13B", "13C", "13D", "13E",
                                "13F", "14A", "14B", "14C", "14D", "14E", "14F", "15A", "15B", "15C", "15D", "15E",
                                "15F", "16A", "16B", "16C", "16D", "16E", "16F", "17A", "17B", "17C", "17D", "17E",
                                "17F", "18A", "18B", "18C", "18D", "18E", "18F", "19A", "19B", "19C", "19D", "19E",
                                "19F", "20A", "20B", "20C", "20D", "20E", "20F", "21A", "21B", "21C", "21D", "21E",
                                "21F", "22A", "22B", "22C", "22D", "22E", "22F", "23A", "23B", "23C", "23D", "23E",
                                "23F", "24A", "24B", "24C", "24D", "24E", "24F", "25A", "25B", "25C", "25D", "25E",
                                "25F", "26A", "26B", "26C", "26D", "26E", "26F", "27A", "27B", "27C", "27D", "27E",
                                "27F", "28A", "28B", "28C", "28D", "28E", "28F", "29A", "29B", "29C", "29D", "29E",
                                "29F", "30A", "30B", "30C", "30D", "30E", "30F", "31A", "31B", "31C", "31D", "31E",
                                "31F", "32A", "32B", "32C", "32D", "32E", "32F", "33A", "33B", "33C", "33D", "33E",
                                "33F", "34A", "34B", "34C", "34D", "34E", "34F" };
                List<Booking> bookingsList = new ArrayList<Booking>();
                bookingsList = this.bookingRepository.findByFlightAndFlightDate(f, d);
                List<String> takenSeats = new ArrayList<String>();
                for (Booking booking : bookingsList) {
                        takenSeats.add(0, booking.getSeatNumber());
                }
                String finalSeat = "";
                for (String seat : possibleSeatList) {
                        if (!takenSeats.contains(seat)) {
                                finalSeat = seat;
                        }
                }
                return finalSeat;
        }

        /*
         * Method that returns the number of hours for flight departure
         * from current time.
         */
        public float getTimeToFlightDeparture(Booking b) throws ParseException {
                String date = b.getFlightDate().toString().substring(0, 10);
                String time = b.getFlight().getDepartureTime().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                Date flightDateTime = sdf.parse(date + " " + time);
                String curDate = LocalDate.now().toString();
                String curTime = LocalTime.now().toString();
                Date curDateTime = sdf.parse(curDate + " " + curTime);

                return (curDateTime.getTime() - flightDateTime.getTime()) / (float) (1000 * 60 * 60);
        }

        /*
         * Method that returns true if luggage has been checked in already.
         */
        public Boolean getLuggageCheckInStatus(String bookingId) {
                Booking b = this.bookingRepository.findByBookingId(UUID.fromString(bookingId));
                if (b.getLuggage() != null) {
                        return true;
                }
                return false;
        }

        /*
         * Method that creates a record in the booking table of the database
         * after processing changes on the flight table.
         */
        public Booking book(String userId, String flightId, String seatNumber, String date, Boolean useRewardPoints) {
                try {
                        Date d = new SimpleDateFormat("MM-dd-yyyy").parse(date);
                        User u = this.userRepository.findByUserId(UUID.fromString(userId));
                        Flight f = this.flightRepository.findByFlightId(UUID.fromString(flightId));
                        Integer totalSeatCount = f.getNumSeats();
                        Integer bookedSeatCount = this.bookingRepository.findByFlightAndFlightDate(f, d).size();
                        Integer availableSeats = totalSeatCount - bookedSeatCount;
                        int pointsToReturn = f.getCost();

                        if (seatNumber == null || seatNumber.equals("")) {
                                seatNumber = this.generateSeatNumber(f, d);
                        }
                        if (availableSeats > 0 && !seatNumber.equals("")) {
                                if (useRewardPoints) {
                                        if (u.getRewardsMiles() < pointsToReturn) {
                                                return null;
                                        }
                                        u.setRewardsMiles(u.getRewardsMiles() - pointsToReturn);
                                } else {
                                        u.setRewardsMiles(u.getRewardsMiles() + pointsToReturn / 10);
                                }

                                this.userRepository.save(u);
                                Booking booking = new Booking(u, f, seatNumber, true, useRewardPoints, d);
                                booking = this.bookingRepository.save(booking);
                                return booking;
                        }
                        return null;
                } catch (Exception e) {
                        logger.error("Error while booking!");
                        logger.error(e.getStackTrace());
                        logger.error(e);
                        return null;
                }
        }

        public String checkInUser(UUID bookingId) {
                try {
                        Booking b = this.bookingRepository.findByBookingId(bookingId);
                        if (b == null) {
                                return "No bookingID";
                        }
                        if (Boolean.TRUE.equals(b.getUserCheckIn())) {
                                return "User is checked in already!";
                        } else {
                                if (getTimeToFlightDeparture(b) < 2) {
                                        b.setUserCheckIn(true);
                                        this.bookingRepository.save(b);
                                        return "Successfully checked in";
                                } else {
                                        return "Cannot check in right now";
                                }

                        }
                } catch (Exception e) {
                        logger.error("Error while checking in user!");
                        logger.error(e.getStackTrace());
                        logger.error(e);
                        return "Error occurred";
                }
        }

        public Boolean checkInLuggage(String bookingId, Integer count, float totalWeight) {
                try {
                        Booking b = this.bookingRepository.findByBookingId(UUID.fromString(bookingId));
                        Luggage luggage = new Luggage(count, totalWeight);
                        this.luggageRepository.save(luggage);
                        b.setLuggage(luggage);
                        this.bookingRepository.save(b);
                        return true;
                } catch (Exception e) {
                        logger.error("Error while checking in luggage!");
                        logger.error(e.getStackTrace());
                        logger.error(e);
                        e.printStackTrace(new java.io.PrintStream(System.err));
                }
                return false;
        }

        public Boolean deleteBooking(String bookingID, String userID) {
                Booking booking = this.bookingRepository.findByBookingId(UUID.fromString(bookingID));

                if (UUID.fromString(userID) != booking.getUser().getUserId()) {
                        return false;
                }

                this.bookingRepository.deleteById(UUID.fromString(bookingID));
                updateRewardPointsForBookingDeletion(booking.getUser().getUserId(), booking.getFlight().getCost(),
                                booking.getFlightDate());

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

                return (this.userRepository.save(user) != null);
        }
}
