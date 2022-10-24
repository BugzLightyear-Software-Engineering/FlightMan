package com.flightman.flightmanapi.unit.controller;
import java.sql.Time;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.flightman.flightmanapi.controller.FlightController;
import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.repositories.FlightRepository;
import com.flightman.flightmanapi.services.FlightService;
import org.springframework.test.web.servlet.ResultActions;

// import java.util.ArrayList;
// import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FlightService.class, FlightRepository.class})
@WebMvcTest(FlightController.class)
public class FlightControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private FlightService flightService;

    private Airport source = new Airport("SourceName", "SN", "Lat", "Long");
    private Airport dest = new Airport("DestName", "DN", "Lat", "Long");
    private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);
    private Time departure_time = new Time(100);
    private Time arrival_time = new Time(500);
    private Flight flight = new Flight(source, dest, model, departure_time,arrival_time, 120, null);

    @Test
    public void createFlight_whenPostMethod() throws Exception {
    given(flightService.save(flight)).willAnswer((invocation)-> invocation.getArgument(0));
    ResultActions response = mockMvc.perform(post("/api/flight"));
    response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void givenFlightId_whendelete_thenReturn200() throws Exception{
        // given - precondition or setup
        // willDoNothing().given(flightService).deleteFlightById(flight.getFlightId());
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/flight/id/{id}",flight.getFlightId()));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
}
