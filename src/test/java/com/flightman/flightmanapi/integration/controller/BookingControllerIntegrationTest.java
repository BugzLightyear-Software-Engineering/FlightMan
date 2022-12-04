package com.flightman.flightmanapi.integration.controller;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

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
import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.repositories.BookingRepository;
import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.services.BookingService;
import com.flightman.flightmanapi.repositories.FlightRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookingController.class)
@ActiveProfiles
public class BookingControllerIntegrationTest {
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

        private String user = "abhilash";
        private String password = "securedpasswordofsrishti";

        @Test
        public void getBooking() throws Exception {
                List<Booking> created = new ArrayList<Booking>();
                created.add(new Booking());
                given(bookingService.get(null)).willReturn(created);
                mockMvc.perform(
                                get("/api/bookings")
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isOk());
        }

        @Test
        public void userCheckIn() throws Exception {
                given(bookingService.checkInUser(UUID.fromString("7a9223a4-820e-42d8-922b-162cea9e5f6e")))
                                .willReturn("Successfully checked in");
                mockMvc.perform(
                                post("/api/bookings/id/{id}/usercheckin",
                                                UUID.fromString("7a9223a4-820e-42d8-922b-162cea9e5f6e"))
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes())))
                                .andExpect(status().isOk());
        }
}
