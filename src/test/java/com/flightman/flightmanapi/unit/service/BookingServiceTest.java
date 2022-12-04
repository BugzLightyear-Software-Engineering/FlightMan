package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.UUID;
import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.model.Luggage;
import com.flightman.flightmanapi.repositories.AirportRepository;
import com.flightman.flightmanapi.repositories.BookingRepository;

import com.flightman.flightmanapi.repositories.FlightModelRepository;
import com.flightman.flightmanapi.repositories.FlightRepository;
import com.flightman.flightmanapi.repositories.LuggageRepository;

import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.services.BookingService;
import com.flightman.flightmanapi.services.FlightService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BookingService.class, BookingRepository.class, FlightRepository.class,
                FlightService.class })
public class BookingServiceTest {
        @MockBean
        private BookingRepository bookingRepository;

        @MockBean
        private FlightRepository flightRepository;

        @MockBean
        private UserRepository userRepository;

        @MockBean
        private LuggageRepository luggageRepository;

        @MockBean
        private FlightModelRepository flightModelRepository;

        @MockBean
        private AirportRepository airportRepository;

        @Autowired
        @InjectMocks
        private BookingService bookingService;

        private String validUser = "6ec95abc-2d4d-46ec-9174-bd595d380ed8";
        private String validFlight = "4a01bbd4-9d7c-4380-a266-b42ee4c27162";
        private String validBooking = "7a9223a4-820e-42d8-922b-162cea9e5f6e";

        User user = new User("FN", "LN", "123456789", "r@domain.com",
                        "passportNumber", "Address", 1, 0);

        private Format f = new SimpleDateFormat("MM-dd-yyyy");
        private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        Booking booking = new Booking();

        Luggage randomLuggage = new Luggage(1, 25);

        private Airport source = new Airport("SourceName", "SN", "Lat", "Long");
        private Airport dest = new Airport("DestName", "DN", "Lat", "Long");
        private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);
        private Time departure_time = new Time(100);
        private Time arrival_time = new Time(500);
        private Flight flight = new Flight(source, dest, model, departure_time, arrival_time, null, 100);
        private String seatnumber = "1A";
        private Boolean paymentStatus = true;

        @Test
        public void shouldReturnAllBookings() {
                List<Booking> bookings = new ArrayList<Booking>();
                bookings.add(new Booking());
                when(bookingRepository.findAll()).thenReturn(bookings);
                List<Booking> expected = bookingService.get(null);
                assertEquals(expected, bookings);
                verify(bookingRepository).findAll();
        }

        @Test
        public void testUserRewardPointChangesForCreation() throws Exception {
                int initial = 100;
                User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0,
                                initial);

                when(userRepository.findByUserId(any())).thenReturn(user);
                when(flightRepository.findByFlightId(any())).thenReturn(flight);

                when(userRepository.save(any())).thenReturn(true);
                when(bookingRepository.save(any())).thenReturn(new Booking());
                when(flightRepository.save(any())).thenReturn(true);

                Booking expected = bookingService.book(
                                "7a9223a4-820e-42d8-922b-162cea9e5f6e",
                                "7a9223a4-820e-42d8-922b-162cea9e5f6e",
                                "1A",
                                "01-01-2022",
                                false);

                assert user.getRewardsMiles() > initial;
        }

        @Test
        public void testUserRewardPointChangesForCreationUsingPoints_1() throws Exception {
                int initial = 100;
                User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0,
                                initial);

                when(userRepository.findByUserId(any())).thenReturn(user);
                when(flightRepository.findByFlightId(any())).thenReturn(flight);

                when(userRepository.save(any())).thenReturn(true);
                when(bookingRepository.save(any())).thenReturn(true);
                when(flightRepository.save(any())).thenReturn(true);

                Booking expected = bookingService.book(
                                "7a9223a4-820e-42d8-922b-162cea9e5f6e",
                                "7a9223a4-820e-42d8-922b-162cea9e5f6e",
                                "1A",
                                "01-01-2022",
                                true);

                assert user.getRewardsMiles() == 0;
        }

        @Test
        public void testUserRewardPointChangesForCreationUsingPoints_2() throws Exception {
                int initial = 50;
                User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0,
                                initial);

                when(userRepository.findByUserId(any())).thenReturn(user);
                when(flightRepository.findByFlightId(any())).thenReturn(flight);

                when(userRepository.save(any())).thenReturn(true);
                when(bookingRepository.save(any())).thenReturn(new Booking());
                when(flightRepository.save(any())).thenReturn(true);

                Booking expected = bookingService.book(
                                "7a9223a4-820e-42d8-922b-162cea9e5f6e",
                                "7a9223a4-820e-42d8-922b-162cea9e5f6e",
                                "1A",
                                "01-01-2022",
                                true);

                assert user.getRewardsMiles() == 50;
                assert expected == null;
        }

        @Test
        public void testUserRewardPointChangesForDeletion_1() {
                int initial = 100;
                User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0,
                                initial);

                when(userRepository.findByUserId(any())).thenReturn(user);
                when(userRepository.save(any())).thenReturn(user);

                Calendar cal = Calendar.getInstance();
                Date today = cal.getTime();
                cal.add(Calendar.YEAR, 10);
                Date later = cal.getTime();

                Boolean expected = bookingService.updateRewardPointsForBookingDeletion(
                                user.getUserId(),
                                100,
                                later);

                assert expected == true;
                assert user.getRewardsMiles() > initial;
        }

        @Test
        public void bookSuccess() throws ParseException {
                Date tomorrowDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
                String tomorrowDateString = f.format(tomorrowDate);
                tomorrowDate = formatter.parse(tomorrowDateString);

                List<Booking> bookings = new ArrayList<Booking>();
                when(bookingRepository.findByFlightAndFlightDate(any(), any())).thenReturn(bookings);
                when(flightRepository.findByFlightId(any())).thenReturn(flight);
                when(userRepository.findByUserId(any())).thenReturn(user);
                when(bookingRepository.save(any())).thenReturn(booking);
                Booking expected = bookingService.book(validUser, validFlight, null, tomorrowDateString, false);
                assertNotNull(expected);
        }

        @Test
        public void getLuggageCheckInStatusTrue() throws ParseException {
                Luggage luggage = new Luggage();
                booking.setLuggage(luggage);
                when(bookingRepository.findByBookingId(any())).thenReturn(booking);
                Boolean isLuggageCheckIn = bookingService.getLuggageCheckInStatus(validBooking);
                assertTrue(isLuggageCheckIn);
        }

        @Test
        public void getLuggageCheckInStatusFalse() throws ParseException {
                booking.setLuggage(null);
                when(bookingRepository.findByBookingId(any())).thenReturn(booking);
                Boolean isLuggageCheckIn = bookingService.getLuggageCheckInStatus(validBooking);
                assertFalse(isLuggageCheckIn);
        }

        @Test
        public void deleteBookingSuccess() {
                Date tomorrowDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
                UUID bookingId = UUID.fromString("11d5f8dd-7fba-4a10-803d-a440e938bfa5");
                UUID userId = UUID.fromString("e9e7e949-8580-411a-bea0-55ff00f72457");
                UUID flightId = UUID.randomUUID();
                user.setUserId(userId);
                flight.setFlightId(flightId);
                booking.setBookingId(bookingId);
                booking.setUser(user);
                booking.setFlight(flight);
                booking.setFlightDate(tomorrowDate);
                when(bookingRepository.findByBookingId(any())).thenReturn(booking);
                when(bookingRepository.deleteById(booking.getBookingId())).thenReturn(0);
                when(userRepository.findByUserId(userId)).thenReturn(user);
                Boolean isDeleteBookingSuccess = bookingService.deleteBooking(booking.getBookingId().toString(),
                                userId.toString());
                assertTrue(isDeleteBookingSuccess);
        }

        @Test
        public void deleteBookingFailureInvalidUser() {
                Date tomorrowDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
                UUID bookingId = UUID.fromString("11d5f8dd-7fba-4a10-803d-a440e938bfa5");
                UUID userId = UUID.fromString("e9e7e949-8580-411a-bea0-55ff00f72457");
                UUID flightId = UUID.randomUUID();
                user.setUserId(userId);
                flight.setFlightId(flightId);
                booking.setBookingId(bookingId);
                booking.setUser(user);
                booking.setFlight(flight);
                booking.setFlightDate(tomorrowDate);
                when(bookingRepository.findByBookingId(any())).thenReturn(booking);
                when(bookingRepository.deleteById(booking.getBookingId())).thenReturn(0);
                when(userRepository.findByUserId(userId)).thenReturn(user);
                Boolean isDeleteBookingSuccess = bookingService.deleteBooking(booking.getBookingId().toString(),
                                "123");
                assertFalse(isDeleteBookingSuccess);
        }

        @Test
        public void testUserRewardPointChangesForDeletion_2() {
                int initial = 100;
                User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0,
                                initial);

                when(userRepository.findByUserId(any())).thenReturn(user);
                when(userRepository.save(any())).thenReturn(user);

                Calendar cal = Calendar.getInstance();
                Date today = cal.getTime();
                cal.add(Calendar.YEAR, -10);
                Date later = cal.getTime();

                Boolean expected = bookingService.updateRewardPointsForBookingDeletion(
                                user.getUserId(),
                                100,
                                later);

                assert expected == true;
                assert user.getRewardsMiles() == initial;
        }

        @Test
        public void testUserCheckIn() throws ParseException {

                Date d = new SimpleDateFormat("MM-dd-yyyy").parse("01-01-2022");
                departure_time = new Time(1 * 60 * 60 * 1000);
                flight = new Flight(source, dest, model, departure_time, arrival_time, null, 100);
                Booking expected = new Booking(user, flight, "1A", true, false, d);
                expected.setBookingId(UUID.randomUUID());
                when(bookingRepository.findByBookingId(expected.getBookingId())).thenReturn(expected);
                String s = bookingService.checkInUser(expected.getBookingId());
                assert (s == "Error occurred");

                UUID notInDB = UUID.randomUUID();
                when(bookingRepository.findByBookingId(notInDB)).thenReturn(null);
                s = bookingService.checkInUser(notInDB);
                assert (s == "No bookingID");

                expected.setUserCheckIn(true);
                s = bookingService.checkInUser(expected.getBookingId());
                assert (s == "User is checked in already!");

                // TODO: check difference in time

        }
}
