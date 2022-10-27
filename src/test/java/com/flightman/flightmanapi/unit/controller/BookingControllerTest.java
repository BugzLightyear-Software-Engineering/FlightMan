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


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookingController.class)
@ActiveProfiles
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookingService bookingService;


    @Test
    public void getBooking() throws Exception {
        List<Booking> created = new ArrayList<Booking>();
        created.add(new Booking());
        given(bookingService.get(null)).willReturn(created);
        mockMvc.perform(get("/api/bookings").accept(MediaType.ALL)).andExpect(status().isOk());
    }
}