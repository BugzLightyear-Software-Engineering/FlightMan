package com.flightman.flightmanapi.unit.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        private String validUser = "6ec95abc-2d4d-46ec-9174-bd595d380ed8";
        private String validFlight = "4a01bbd4-9d7c-4380-a266-b42ee4c27162";
        private String invalidUser = "123";
        private String invalidFlight = "456";

        private Format f = new SimpleDateFormat("MM-dd-yyyy");
        private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        Booking booking = new Booking();

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
        public void createBookingSuccess() throws Exception {
                /* Test happy path */

                Date tomorrowDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
                String tomorrowDateString = f.format(tomorrowDate);
                tomorrowDate = formatter.parse(tomorrowDateString);

                when(bookingService.book(validUser, validFlight, null, tomorrowDateString, false))
                                .thenReturn(booking);
                when(bookingService.validateUser(validUser)).thenReturn(true);
                when(bookingService.validateFlight(validFlight)).thenReturn(true);
                mockMvc.perform(
                                post("/api/bookings?userId=" + validUser + "&flightId=" + validFlight + "&date="
                                                + tomorrowDateString)
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes())))
                                .andExpect(status().isCreated());

        }

        @Test
        public void createBookingBadDate() throws Exception {
                /* Test sad path */

                Date yesterdayDate = new Date(new Date().getTime() - (1000 * 60 * 60 * 24));
                String yesterdayDateString = f.format(yesterdayDate);
                yesterdayDate = formatter.parse(yesterdayDateString);

                mockMvc.perform(
                                post("/api/bookings?userId=" + validUser + "&flightId=" + validFlight + "&date="
                                                + yesterdayDateString)
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes())))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void createBookingBadUser() throws Exception {
                /* Test sad path */

                Date tomorrowDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
                String tomorrowDateString = f.format(tomorrowDate);
                tomorrowDate = formatter.parse(tomorrowDateString);

                when(bookingService.validateUser(invalidUser)).thenReturn(false);
                mockMvc.perform(
                                post("/api/bookings?userId=" + invalidUser + "&flightId=" + validFlight + "&date="
                                                + tomorrowDateString)
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes())))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void createBookingBadFlight() throws Exception {
                /* Test sad path */

                Date tomorrowDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
                String tomorrowDateString = f.format(tomorrowDate);
                tomorrowDate = formatter.parse(tomorrowDateString);

                when(bookingService.validateFlight(invalidFlight)).thenReturn(false);
                mockMvc.perform(
                                post("/api/bookings?userId=" + validUser + "&flightId=" + invalidFlight + "&date="
                                                + tomorrowDateString)
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes())))
                                .andExpect(status().isBadRequest());
        }
}