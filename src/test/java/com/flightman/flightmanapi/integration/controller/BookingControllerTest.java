package com.flightman.flightmanapi.unit.controller;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.flightman.flightmanapi.controller.BookingController;
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

import org.mockito.InjectMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookingController.class)
@ActiveProfiles
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private BookingService bookingService;

    private Airport source = new Airport("SourceName", "SN", "Lat", "Long");
    private Airport dest = new Airport("DestName", "DN", "Lat", "Long");
    private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);
    private Time departure_time = new Time(100);
    private Time arrival_time = new Time(500);
    private Flight flight = new Flight(source, dest, model, departure_time,arrival_time, 120, null, 100);

    private String user = "abhilash";
    private String password = "securedpasswordofsrishti";

    @Test
    public void getBooking() throws Exception {
        List<Booking> created = new ArrayList<Booking>();
        created.add(new Booking());
        given(bookingService.get(null)).willReturn(created);
        mockMvc.perform(
                        get("/api/bookings")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    public void createBooking_1() throws Exception {
        given(bookingService.book(any(), any(), any(), any(), any(), any())).willReturn(new Booking());
        mockMvc.perform(
                        post("/api/bookings")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"flightId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"seatNumber\": \"1\",\"useRewardPoints\": \"true\", \"date\": \"2022-01-01\"}"))
                        .andExpect(status().isOk());
    }

    @Test
    public void deleteBooking() throws Exception {
        given(bookingService.deleteBooking(any(), any())).willReturn(true);
        mockMvc.perform(
                        delete("/api/bookings")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookingId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"userId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\"}"))
                        .andExpect(status().isOk());
    }

    @Test
    public void createBooking_2() throws Exception {
        int initial = 100;
        User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0, initial);

        when(userRepository.findByUserId(any())).thenReturn(user);
        when(flightRepository.findByFlightId(any())).thenReturn(flight);

        when(userRepository.save(any())).thenReturn(true);
        when(bookingRepository.save(any())).thenReturn(true);
        when(flightRepository.save(any())).thenReturn(true);

        mockMvc.perform(
                        post("/api/bookings")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"flightId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"seatNumber\": \"1\",\"useRewardPoints\": \"true\", \"date\": \"2022-01-01\"}"));
    }

    @Test
    public void createBooking_3() throws Exception {
        int initial = 50;
        User user = new User("First", "Last", "123456789", "email@email.com", "ABC456789", "Address", 0, initial);

        when(userRepository.findByUserId(any())).thenReturn(user);
        when(flightRepository.findByFlightId(any())).thenReturn(flight);

        when(userRepository.save(any())).thenReturn(true);
        when(bookingRepository.save(any())).thenReturn(true);
        when(flightRepository.save(any())).thenReturn(true);

        mockMvc.perform(
                        post("/api/bookings")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"flightId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"seatNumber\": \"1\",\"useRewardPoints\": \"false\", \"date\": \"2022-01-01\"}"));
    }
}
