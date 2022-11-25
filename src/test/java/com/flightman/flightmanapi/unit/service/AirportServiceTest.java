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

import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.repositories.AirportRepository;
import com.flightman.flightmanapi.services.AirportService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AirportService.class, AirportRepository.class })
public class AirportServiceTest {
        @MockBean
        private AirportRepository airportRepository;

        @Autowired
        @InjectMocks
        private AirportService airportService;

        private Airport airport = new Airport("AirportName", "APN", "1", "2");

        @Test
        public void whenSaveAirport_shouldReturnAirport() {
                Mockito.when(airportRepository.save(ArgumentMatchers.any(Airport.class))).thenReturn(airport);
                Boolean created = airportService.saveAirport(airport);
                assert (created == true);
                verify(airportRepository).save(airport);
        }

        @Test
        public void shouldReturnAllAirports() {
                List<Airport> airports = new ArrayList<Airport>();
                airports.add(new Airport());
                when(airportRepository.findAll()).thenReturn(airports);
                List<Airport> expected = airportService.find(null);
                assertEquals(expected, airports);
                verify(airportRepository).findAll();

        }

}