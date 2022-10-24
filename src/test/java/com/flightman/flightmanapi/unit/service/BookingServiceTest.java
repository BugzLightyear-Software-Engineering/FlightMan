package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.repositories.BookingRepository;
import com.flightman.flightmanapi.repositories.FlightRepository;
import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.services.BookingService;
import com.flightman.flightmanapi.services.FlightService;


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

    @Test
    public void shouldReturnAllBookings(){
        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);
        List<Booking> expected = bookingService.get(null);
        assertEquals(expected, bookings);
        verify(bookingRepository).findAll();

    }

}