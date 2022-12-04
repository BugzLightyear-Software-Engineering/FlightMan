package com.flightman.flightmanapi.integration.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        private Airport source2 = new Airport("SourceName2", "SN2", "Lat", "Long");
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
                
                List<Flight> created_empty = new ArrayList<Flight>();
                when(flightService.getFlights(source2.getAirportAbvName(),null)).thenReturn(created_empty);
                mockMvc.perform(
                                get("/api/flights?sourceAbv=SN")
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isNoContent());
        }

        @Test
        public void deleteFlightById() throws Exception{
                flight.setFlightId(UUID.randomUUID());
                when(flightService.deleteFlightById(flight.getFlightId())).thenReturn(1);
                mockMvc.perform(
                                delete("/api/flight/id/{id}", flight.getFlightId())
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").value(true));
                
                UUID notInDB = UUID.randomUUID();
                when(flightService.deleteFlightById(notInDB)).thenReturn(0);
                mockMvc.perform(
                                delete("/api/flight/id/{id}", notInDB)
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isBadRequest());

        }
        @Test
        public void updateFlightById() throws Exception{
                flight.setFlightId(UUID.randomUUID());
                when(flightService.update(flight.getFlightId(), null, null, null)).thenReturn(flight);
                mockMvc.perform(
                                put("/api/flight/id/{id}", flight.getFlightId())
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isOk());
                UUID noInDB = UUID.randomUUID();
                when(flightService.update(noInDB, null, null, null)).thenReturn(null);
                mockMvc.perform(
                                put("/api/flight/id/{id}",  noInDB)
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void createFlightSaveError() throws Exception{
                when(flightService.validateAirport(UUID.fromString("7199de04-60d7-45c4-9d01-d0a1ea807f73"))).thenReturn(true);
                when(flightService.validateAirport(UUID.fromString("d4005cf1-7842-44a7-9c34-77314d432e64"))).thenReturn(true);
                when(flightService.validateAirport(UUID.fromString("7199de04-60d7-45c4-9d01-d0a1ea807f74"))).thenReturn(false);
                when(flightService.validateAirport(UUID.fromString("d4005cf1-7842-44a7-9c34-77314d432e65"))).thenReturn(false);
                when(flightService.validateFlightModel(2)).thenReturn(true);
                when(flightService.validateFlightModel(10)).thenReturn(false);
                when(flightService.save(null)).thenReturn(null);
                mockMvc.perform(
                        post("/api/flight")
                                        .header(HttpHeaders.AUTHORIZATION,
                                                        "Basic " + Base64Utils.encodeToString(
                                                                        (this.user + ":" + this.password)
                                                                                        .getBytes()))
                                        .content("{\"sourceAirport\": {\"airportId\": \"7199de04-60d7-45c4-9d01-d0a1ea807f73\",\"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"destAirport\": {\"airportId\": \"d4005cf1-7842-44a7-9c34-77314d432e64\", \"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"flightModel\": {\"flightModelId\": \"2\",\"flightManufacturerName\": \"aml\",\"flightModelNumber\": \"723e\",\"seatCapacity\": \"600\",\"seatRowCount\": \"60\",\"seatColCount\": \"10\"},\"departureTime\": \"09:00:00\",\"estArrivalTime\": \"10:00:00\",\"delayTime\": \"00:00:00\",\"numSeats\": \"600\",\"cost\":\"100\" }")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.ALL))
                        .andExpect(status().isBadRequest());
        }
        
        @Test
        public void createFlight() throws Exception{
                when(flightService.validateAirport(UUID.fromString("7199de04-60d7-45c4-9d01-d0a1ea807f73"))).thenReturn(true);
                when(flightService.validateAirport(UUID.fromString("d4005cf1-7842-44a7-9c34-77314d432e64"))).thenReturn(true);
                when(flightService.validateAirport(UUID.fromString("7199de04-60d7-45c4-9d01-d0a1ea807f74"))).thenReturn(false);
                when(flightService.validateAirport(UUID.fromString("d4005cf1-7842-44a7-9c34-77314d432e65"))).thenReturn(false);
                when(flightService.validateFlightModel(2)).thenReturn(true);
                when(flightService.validateFlightModel(10)).thenReturn(false);
                when(flightService.save(any())).thenReturn(flight);
                mockMvc.perform(
                        post("/api/flight")
                                        .header(HttpHeaders.AUTHORIZATION,
                                                        "Basic " + Base64Utils.encodeToString(
                                                                        (this.user + ":" + this.password)
                                                                                        .getBytes()))
                                        .content("{\"sourceAirport\": {\"airportId\": \"7199de04-60d7-45c4-9d01-d0a1ea807f73\",\"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"destAirport\": {\"airportId\": \"d4005cf1-7842-44a7-9c34-77314d432e64\", \"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"flightModel\": {\"flightModelId\": \"2\",\"flightManufacturerName\": \"aml\",\"flightModelNumber\": \"723e\",\"seatCapacity\": \"600\",\"seatRowCount\": \"60\",\"seatColCount\": \"10\"},\"departureTime\": \"09:00:00\",\"estArrivalTime\": \"10:00:00\",\"delayTime\": \"00:00:00\",\"numSeats\": \"600\",\"cost\":\"100\" }")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.ALL))
                        .andExpect(status().isOk());
                mockMvc.perform(
                        post("/api/flight")
                                        .header(HttpHeaders.AUTHORIZATION,
                                                        "Basic " + Base64Utils.encodeToString(
                                                                        (this.user + ":" + this.password)
                                                                                        .getBytes()))
                                        .content("{\"sourceAirport\": {\"airportId\": \"7199de04-60d7-45c4-9d01-d0a1ea807f74\",\"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"destAirport\": {\"airportId\": \"d4005cf1-7842-44a7-9c34-77314d432e64\", \"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"flightModel\": {\"flightModelId\": \"2\",\"flightManufacturerName\": \"aml\",\"flightModelNumber\": \"723e\",\"seatCapacity\": \"600\",\"seatRowCount\": \"60\",\"seatColCount\": \"10\"},\"departureTime\": \"09:00:00\",\"estArrivalTime\": \"10:00:00\",\"delayTime\": \"00:00:00\",\"numSeats\": \"600\",\"cost\":\"100\" }")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.ALL))
                        .andExpect(status().isBadRequest());
                mockMvc.perform(
                        post("/api/flight")
                                        .header(HttpHeaders.AUTHORIZATION,
                                                        "Basic " + Base64Utils.encodeToString(
                                                                        (this.user + ":" + this.password)
                                                                                        .getBytes()))
                                        .content("{\"sourceAirport\": {\"airportId\": \"7199de04-60d7-45c4-9d01-d0a1ea807f73\",\"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"destAirport\": {\"airportId\": \"d4005cf1-7842-44a7-9c34-77314d432e65\", \"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"flightModel\": {\"flightModelId\": \"2\",\"flightManufacturerName\": \"aml\",\"flightModelNumber\": \"723e\",\"seatCapacity\": \"600\",\"seatRowCount\": \"60\",\"seatColCount\": \"10\"},\"departureTime\": \"09:00:00\",\"estArrivalTime\": \"10:00:00\",\"delayTime\": \"00:00:00\",\"numSeats\": \"600\",\"cost\":\"100\" }")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.ALL))
                                .andExpect(status().isBadRequest());
                mockMvc.perform(
                                post("/api/flight")
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .content("{\"sourceAirport\": {\"airportId\": \"7199de04-60d7-45c4-9d01-d0a1ea807f73\",\"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"destAirport\": {\"airportId\": \"d4005cf1-7842-44a7-9c34-77314d432e64\", \"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"flightModel\": {\"flightModelId\": \"10\",\"flightManufacturerName\": \"aml\",\"flightModelNumber\": \"723e\",\"seatCapacity\": \"600\",\"seatRowCount\": \"60\",\"seatColCount\": \"10\"},\"departureTime\": \"09:00:00\",\"estArrivalTime\": \"10:00:00\",\"delayTime\": \"00:00:00\",\"numSeats\": \"600\",\"cost\":\"100\" }")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.ALL))
                                .andExpect(status().isBadRequest());
                mockMvc.perform(
                                post("/api/flight")
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .content("{\"sourceAirport\": {\"airportId\": \"7199de04-60d7-45c4-9d01-d0a1ea807f73\",\"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"destAirport\": {\"airportId\": \"d4005cf1-7842-44a7-9c34-77314d432e64\", \"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"flightModel\": {\"flightModelId\": \"2\",\"flightManufacturerName\": \"aml\",\"flightModelNumber\": \"723e\",\"seatCapacity\": \"600\",\"seatRowCount\": \"60\",\"seatColCount\": \"10\"},\"departureTime\": \"09:00:00\",\"estArrivalTime\": \"10:00:00\",\"delayTime\": \"00:00:00\",\"numSeats\": \"600\",\"cost\":\"-100\" }")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.ALL))
                                .andExpect(status().isBadRequest());
                
                when(flightService.save(any())).thenThrow(new NullPointerException());
                mockMvc.perform(
                                post("/api/flight")
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.user + ":" + this.password)
                                                                                                .getBytes()))
                                                .content("{\"sourceAirport\": {\"airportId\": \"7199de04-60d7-45c4-9d01-d0a1ea807f73\",\"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"destAirport\": {\"airportId\": \"d4005cf1-7842-44a7-9c34-77314d432e64\", \"airportName\": \"SourceName\",\"airportAbvName\": \"JFK\",\"latitude\": \"1\",\"longitude\": \"2\"},\"flightModel\": {\"flightModelId\": \"2\",\"flightManufacturerName\": \"aml\",\"flightModelNumber\": \"723e\",\"seatCapacity\": \"600\",\"seatRowCount\": \"60\",\"seatColCount\": \"10\"},\"departureTime\": null,\"estArrivalTime\": \"10:00:00\",\"delayTime\": \"00:00:00\",\"numSeats\": \"600\",\"cost\":\"100\" }")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.ALL))
                                .andExpect(status().isBadRequest());
                        }
        
                

}


