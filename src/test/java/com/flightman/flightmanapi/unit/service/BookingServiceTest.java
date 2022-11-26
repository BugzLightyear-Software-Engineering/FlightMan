package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.repositories.BookingRepository;
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

        @Autowired
        @InjectMocks
        private BookingService bookingService;

        private String validUser = "6ec95abc-2d4d-46ec-9174-bd595d380ed8";
        private String validFlight = "4a01bbd4-9d7c-4380-a266-b42ee4c27162";

        private Airport source = new Airport("SourceName", "SN", "Lat", "Long");
        private Airport dest = new Airport("DestName", "DN", "Lat", "Long");
        private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);
        private Time departure_time = new Time(100);
        private Time arrival_time = new Time(500);
        private Flight flight = new Flight(source, dest, model, departure_time, arrival_time, null);

        User user = new User("FN", "LN", "123456789", "r@domain.com",
                        "passportNumber", "Address", 1, 0);

        private Format f = new SimpleDateFormat("MM-dd-yyyy");
        private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        Booking booking = new Booking();

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
        public void shouldBook() throws ParseException {
                Date tomorrowDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
                String tomorrowDateString = f.format(tomorrowDate);
                tomorrowDate = formatter.parse(tomorrowDateString);

                List<Booking> bookings = new ArrayList<Booking>();
                when(bookingRepository.findByFlightAndFlightDate(any(), any())).thenReturn(bookings);
                when(flightRepository.findByFlightId(any())).thenReturn(flight);
                when(userRepository.findByUserId(any())).thenReturn(user);
                when(bookingRepository.save(any())).thenReturn(booking);
                Booking expected = bookingService.book(validUser, validFlight, null, tomorrowDateString, true);
                assertNotNull(expected);
        }

}