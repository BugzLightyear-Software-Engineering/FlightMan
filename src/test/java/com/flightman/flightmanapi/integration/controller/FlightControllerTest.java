package com.flightman.flightmanapi.integration.controller;

import java.sql.Time;
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

import com.flightman.flightmanapi.controller.FlightController;
import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.services.FlightService;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FlightController.class)
@ActiveProfiles
public class FlightControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private FlightService flightService;

        private String user = "abhilash";
        private String password = "securedpasswordofsrishti";

        private Airport source = new Airport("SourceName", "SN", "Lat", "Long");
        private Airport dest = new Airport("DestName", "DN", "Lat", "Long");
        private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);
        private Time departure_time = new Time(100);
        private Time arrival_time = new Time(500);
        private Flight flight = new Flight(source, dest, model, departure_time, arrival_time, null, 100);

        @Test
        public void getFlights() throws Exception {
                List<Flight> created = new ArrayList<Flight>();
                created.add(flight);
                given(flightService.getFlights(null, null)).willReturn(created);
                mockMvc.perform(
                                get("/api/flights")
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isOk());
        }

        @Test
        public void getFlightByAbv() throws Exception {
                List<Flight> created = new ArrayList<Flight>();
                created.add(flight);
                when(flightService.getFlights(flight.getSourceAirport().getAirportAbvName(),
                                flight.getDestAirport().getAirportAbvName())).thenReturn(created);
                mockMvc.perform(
                                get("/api/flights?sourceAbv=SN&destAbv=DN")
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isOk());
        }

}

// @Test
// public void createFlight() throws Exception {
// when(flightService.save(any())).thenReturn(flight);
// mockMvc.perform(post("/api/flight").contentType(MediaType.APPLICATION_JSON).content(flight.toString())).andExpect(status().isOk());
// }