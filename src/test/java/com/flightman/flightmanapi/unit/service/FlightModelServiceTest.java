package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.repositories.FlightModelRepository;
import com.flightman.flightmanapi.services.FlightModelService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FlightModelService.class, FlightModelRepository.class})
public class FlightModelServiceTest {
    @MockBean
    private FlightModelRepository flightModelRepository;

    @Autowired
    @InjectMocks
    private FlightModelService flightModelService;

    private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);

    @Test
    public void whenSaveFlightModel_shouldReturnFlightModel() {
        
        
        Mockito.when(flightModelRepository.save(ArgumentMatchers.any(FlightModel.class))).thenReturn(model);
        FlightModel created = flightModelService.save(model);
        assert(created != null);
        verify(flightModelRepository).save(model);
        }

    @Test
    public void shouldReturnAllFlightModels(){
        List<FlightModel> models = new ArrayList<FlightModel>();
        models.add(new FlightModel());
        when(flightModelRepository.findAll()).thenReturn(models);
        List<FlightModel> expected = flightModelService.getAllFlightModels();
        assertEquals(expected, models);
        verify(flightModelRepository).findAll();

    }

    @Test
    public void whenGivenId_shouldDeleteFlightModel_ifFound(){
        when(flightModelRepository.findByFlightModelId(model.getFlightModelId())).thenReturn(model);
        flightModelService.deleteModelById(model.getFlightModelId());
        verify(flightModelRepository).deleteByFlightModelId(model.getFlightModelId());
    }

}