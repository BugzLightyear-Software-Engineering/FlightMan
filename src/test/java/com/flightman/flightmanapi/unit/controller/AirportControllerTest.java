package com.flightman.flightmanapi.unit.controller;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import com.flightman.flightmanapi.controller.AirportController;
import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.services.AirportService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
// @ContextConfiguration(classes = {AirportService.class, AirportRepository.class, AirportController.class})
@WebMvcTest(controllers = AirportController.class)
// @Import(AirportController.class)
@ActiveProfiles
public class AirportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AirportService airportService;

    private Airport source = new Airport("SourceName", "SN", "Lat", "Long");

    private String user = "abhilash";
    private String password = "securedpasswordofsrishti";

    @Test
    public void createAirport() throws Exception {
        System.out.println(source.toString());
        when(airportService.saveAirport(any())).thenReturn(true);
        mockMvc.perform(
                post("/api/airports")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"airportName\": \"SourceName\",\"airportAbvName\": \"SN\",\"latitude\": \"Lat\",\"longitude\": \"Long\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAirport() throws Exception {
        List<Airport> temp = new ArrayList<Airport>();
        temp.add(source);
        given(airportService.find(null)).willReturn(temp);
        mockMvc.perform(
                get("/api/airports")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }
}

//     @Test
//     public void givenFlightId_whendelete_thenReturn200() throws Exception{
//         // given - precondition or setup
//         // willDoNothing().given(airportService).deleteFlightById(flight.getFlightId());
//         // when -  action or the behaviour that we are going test
//         ResultActions response = mockMvc.perform(delete("/api/flight/id/{id}",flight.getFlightId()));

//         // then - verify the output
//         // response.andExpect(status().isNotFound())
//         //         .andDo(print());
//     }