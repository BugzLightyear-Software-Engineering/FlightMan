package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.repositories.BookingRepository;
import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.services.BookingService;
import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.repositories.FlightRepository;
import com.flightman.flightmanapi.services.FlightService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookingService.class, BookingRepository.class, FlightRepository.class, FlightService.class})
public class BookingServiceTest {
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private UserRepository userRepository;
    
    @Autowired
    @InjectMocks
    private BookingService bookingService;

    private Airport source = new Airport("SourceName", "SN", "Lat", "Long");
    private Airport dest = new Airport("DestName", "DN", "Lat", "Long");
    private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);
    private Time departure_time = new Time(100);
    private Time arrival_time = new Time(500);
    private Flight flight = new Flight(source, dest, model, departure_time,arrival_time, 120, null, 100);

    @Test
    public void shouldReturnAllBookings(){
        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);
        List<Booking> expected = bookingService.get(null);
        assertEquals(expected, bookings);
        verify(bookingRepository).findAll();
    }

    @Test
    public void testUserRewardPointChangesForCreation() {
        int initial = 100;
        User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0, initial);

        when(userRepository.findByUserId(any())).thenReturn(user);
        when(flightRepository.findByFlightId(any())).thenReturn(flight);

        when(userRepository.save(any())).thenReturn(true);
        when(bookingRepository.save(any())).thenReturn(new Booking());
        when(flightRepository.save(any())).thenReturn(true);

        Booking expected = bookingService.book(
            "7a9223a4-820e-42d8-922b-162cea9e5f6e",
            "7a9223a4-820e-42d8-922b-162cea9e5f6e",
            "1",
            true,
            false,
            "2000-01-01"
        );

        assert user.getRewardsMiles() > initial;
    }

    @Test
    public void testUserRewardPointChangesForCreationUsingPoints_1() {
        int initial = 100;
        User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0, initial);

        when(userRepository.findByUserId(any())).thenReturn(user);
        when(flightRepository.findByFlightId(any())).thenReturn(flight);

        when(userRepository.save(any())).thenReturn(true);
        when(bookingRepository.save(any())).thenReturn(true);
        when(flightRepository.save(any())).thenReturn(true);

        Booking expected = bookingService.book(
            "7a9223a4-820e-42d8-922b-162cea9e5f6e",
            "7a9223a4-820e-42d8-922b-162cea9e5f6e",
            "1",
            true,
            true,
            "2000-01-01"
        );

        assert user.getRewardsMiles() == 0;
    }

    @Test
    public void testUserRewardPointChangesForCreationUsingPoints_2() {
        int initial = 50;
        User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0, initial);

        when(userRepository.findByUserId(any())).thenReturn(user);
        when(flightRepository.findByFlightId(any())).thenReturn(flight);

        when(userRepository.save(any())).thenReturn(true);
        when(bookingRepository.save(any())).thenReturn(new Booking());
        when(flightRepository.save(any())).thenReturn(true);

        Booking expected = bookingService.book(
            "7a9223a4-820e-42d8-922b-162cea9e5f6e",
            "7a9223a4-820e-42d8-922b-162cea9e5f6e",
            "1",
            true,
            true,
            "2000-01-01"
        );

        assert user.getRewardsMiles() == 50;
        assert expected == null;
    }

    @Test
    public void testUserRewardPointChangesForDeletion_1() {
        int initial = 100;
        User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0, initial);

        when(userRepository.findByUserId(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, 10);
        Date later = cal.getTime();

        Boolean expected = bookingService.updateRewardPointsForBookingDeletion(
            user.getID(),
            100,
            later
        );

        assert expected == true;
        assert user.getRewardsMiles() > initial;
    }

    @Test
    public void testUserRewardPointChangesForDeletion_2() {
        int initial = 100;
        User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0, initial);

        when(userRepository.findByUserId(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, -10);
        Date later = cal.getTime();

        Boolean expected = bookingService.updateRewardPointsForBookingDeletion(
            user.getID(),
            100,
            later
        );

        assert expected == true;
        assert user.getRewardsMiles() == initial;
    }
}
