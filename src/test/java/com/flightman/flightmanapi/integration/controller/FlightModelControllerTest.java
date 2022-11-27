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

import com.flightman.flightmanapi.controller.FlightModelController;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.services.FlightModelService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FlightModelController.class)
@ActiveProfiles
public class FlightModelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private FlightModelService flightModelService;

    private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);

    private String user = "abhilash";
    private String password = "securedpasswordofsrishti";

    @Test
    public void getModels() throws Exception {
        List<FlightModel> created = new ArrayList<FlightModel>();
        created.add(model);
        given(flightModelService.getAllFlightModels()).willReturn(created);
        mockMvc.perform(
                        get("/api/models")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL)).andExpect(status().isOk());
    }

}

    // @Test
    // public void createModels() throws Exception {
    //     given(flightModelService.save(model)).willReturn(model);
    //     mockMvc.perform(
    //                     post("/api/model")
    //                     .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.user + ":" + this.password).getBytes()))
    //                     .content("flightManufacturerName, String flightModelNumber, Integer seatCapacity, Integer seatRowCount, Integer seatColCount")
    //                     .accept(MediaType.ALL)).andExpect(status().isOk());
    // }
    