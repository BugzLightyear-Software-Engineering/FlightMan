package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.repositories.BookingRepository;
import com.flightman.flightmanapi.repositories.FlightModelRepository;
import com.flightman.flightmanapi.repositories.FlightRepository;
import com.flightman.flightmanapi.services.FlightService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FlightService.class, FlightRepository.class})
public class FlightServiceTest {
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private FlightModelRepository flightModelRepository;

    @Autowired
    @InjectMocks
    private FlightService flightService;

    private Airport source = new Airport("SourceName", "SN", "Lat", "Long");
    private Airport dest = new Airport("DestName", "DN", "Lat", "Long");
    private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);
    private Time departure_time = new Time(100);
    private Time arrival_time = new Time(500);
    private Flight flight = new Flight(source, dest, model, departure_time,arrival_time, 120, null);

    @Test
    public void whenSaveFlight_shouldReturnFlight() {
        
        
        Mockito.when(flightRepository.save(ArgumentMatchers.any(Flight.class))).thenReturn(flight);
        Flight created = flightService.save(flight);
        assert(created.getDestAirport().getAirportId()) == (flight.getDestAirport().getAirportId());
        assert(created.getSourceAirport().getAirportId()) == (flight.getSourceAirport().getAirportId());
        assert(created.getFlightModel().getFlightModelId()) == (flight.getFlightModel().getFlightModelId());
        assert(created.getDepartureTime() == flight.getDepartureTime());
        assert(created.getEstArrivalTime() == flight.getEstArrivalTime());
        assert(created.getNumSeats() == flight.getNumSeats());
        verify(flightRepository).save(flight);
        }

    @Test
    public void shouldReturnAllFlights(){
        List<Flight> flights = new ArrayList<Flight>();
        flights.add(new Flight());
        when(flightRepository.findAll()).thenReturn(flights);
        List<Flight> expected = flightService.getAllFlights();
        assertEquals(expected, flights);
        verify(flightRepository).findAll();

    }

    @Test
    public void whenGivenId_shouldDeleteFlight_ifFound(){
        when(bookingRepository.deleteByFlight(flight)).thenReturn((long) 0);
        when(flightRepository.deleteByFlightId(flight.getFlightId())).thenReturn(1);
        flightService.deleteFlightById(flight.getFlightId());
        verify(flightRepository).deleteByFlightId(flight.getFlightId());
    }

}