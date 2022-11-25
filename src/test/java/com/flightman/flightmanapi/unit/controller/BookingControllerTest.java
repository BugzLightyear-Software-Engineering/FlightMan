package com.flightman.flightmanapi.unit.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.flightman.flightmanapi.services.BookingService;

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
    public void createBooking() throws Exception {
        /* Test happy path */
        when(bookingService.book()).thenReturn(true);
        mockMvc.perform(
                post("/api/airports")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"}"))
                .andExpect(status().isOk());
    }
}