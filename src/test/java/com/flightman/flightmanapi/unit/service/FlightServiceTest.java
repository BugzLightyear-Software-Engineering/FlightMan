package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Time;
import java.util.List;
import java.util.UUID;
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
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.repositories.AirportRepository;
import com.flightman.flightmanapi.repositories.BookingRepository;
import com.flightman.flightmanapi.repositories.FlightModelRepository;
import com.flightman.flightmanapi.repositories.FlightRepository;
import com.flightman.flightmanapi.services.FlightService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { FlightService.class, FlightRepository.class })
public class FlightServiceTest {
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private FlightModelRepository flightModelRepository;

    @MockBean
    private AirportRepository airportRepository;

    @Autowired
    @InjectMocks
    private FlightService flightService;

    private Airport source = new Airport("SourceName", "SN", "Lat", "Long");
    private Airport dest = new Airport("DestName", "DN", "Lat", "Long");
    private FlightModel model = new FlightModel("MName", "123a", 120, 20, 6);
    private Time departure_time = new Time(100);
    private Time arrival_time = new Time(500);
    private Flight flight = new Flight(source, dest, model, departure_time, arrival_time, null, 100);

    private FlightModel new_model = new FlightModel("MNameNew", "123aNew", 120, 20, 6);
    private Time new_departure_time = new Time(200);
    private Time new_arrival_time = new Time(600);

    @Test void validateModel(){
        model.setFlightModelId(1);
        Mockito.when(flightModelRepository.findByFlightModelId(1)).thenReturn(model);
        Mockito.when(flightModelRepository.findByFlightModelId(2)).thenReturn(null);
        Boolean trueB = flightService.validateFlightModel(1);
        Boolean falseB = flightService.validateFlightModel(2);
        assert(trueB == true);
        assert(falseB == false);

    }
    @Test void validateAirport(){
        UUID inDb = UUID.randomUUID();
        UUID notInDb = UUID.randomUUID();
        source.setAirportId(inDb);
        Mockito.when(airportRepository.findByAirportId(inDb)).thenReturn(source);
        Mockito.when(airportRepository.findByAirportId(notInDb)).thenReturn(null);
        Boolean trueB = flightService.validateAirport(inDb);
        Boolean falseB = flightService.validateAirport(notInDb);
        assert(trueB == true);
        assert(falseB == false);

    }
    @Test
    public void whenSaveFlight_shouldReturnFlight() {

            Mockito.when(flightRepository.save(ArgumentMatchers.any(Flight.class))).thenReturn(flight);
            Flight created = flightService.save(flight);
            assert (created.getDestAirport().getAirportId()) == (flight.getDestAirport().getAirportId());
            assert (created.getSourceAirport().getAirportId()) == (flight.getSourceAirport().getAirportId());
            assert (created.getFlightModel().getFlightModelId()) == (flight.getFlightModel().getFlightModelId());
            assert (created.getDepartureTime() == flight.getDepartureTime());
            assert (created.getEstArrivalTime() == flight.getEstArrivalTime());
            assert (created.getNumSeats() == flight.getNumSeats());
            verify(flightRepository).save(flight);
    }

    @Test
    public void whenUpdateFlight_shouldReturnFlight() {
            new_model.setFlightModelId(10);


            Mockito.when(flightRepository.findByFlightId(flight.getFlightId())).thenReturn(flight);
            Mockito.when(flightRepository.save(any())).thenReturn(flight);
            Mockito.when(flightModelRepository.findByFlightModelId(10)).thenReturn(new_model);
            Mockito.when(flightModelRepository.findByFlightModelId(11)).thenReturn(null);

            Flight updated = flightService.update(flight.getFlightId(), new_departure_time, new_arrival_time, new_model.getFlightModelId());
            flight.setDepartureTime(new_departure_time);
            flight.setEstArrivalTime(new_arrival_time);
            flight.setFlightModel(new_model);
            
            
            assert (flight.getFlightModel().getFlightModelId()) == (updated.getFlightModel().getFlightModelId());
            assert (flight.getDepartureTime() == updated.getDepartureTime());
            assert (flight.getEstArrivalTime() == updated.getEstArrivalTime());
            verify(flightRepository).save(updated);

            Flight updated2 = flightService.update(flight.getFlightId(), new_departure_time, new_arrival_time, 11);
            assert (updated2.getFlightModel().getFlightModelId()) == 10;

            UUID notInDb = UUID.randomUUID();
            Mockito.when(flightRepository.findByFlightId(notInDb)).thenReturn(null);
            Flight noFlight = flightService.update(notInDb, new_departure_time, new_arrival_time, new_model.getFlightModelId());
            assert(noFlight == null);
            
    }

    @Test
    public void whenNoUpdateFlight_shouldReturnFlight(){
        Mockito.when(flightRepository.findByFlightId(flight.getFlightId())).thenReturn(flight);
        Mockito.when(flightRepository.save(any())).thenReturn(flight);

        Flight updated1 = flightService.update(flight.getFlightId(), null, null, null);
        
        assert (flight.getFlightModel().getFlightModelId()) == (updated1.getFlightModel().getFlightModelId());
        assert (flight.getDepartureTime() == updated1.getDepartureTime());
        assert (flight.getEstArrivalTime() == updated1.getEstArrivalTime());
        verify(flightRepository).save(updated1);
    }

    @Test
    public void shouldReturnAllFlights() {
            List<Flight> flights = new ArrayList<Flight>();
            flights.add(flight);
            when(flightRepository.findAll()).thenReturn(flights);
            List<Flight> expected = flightService.getAllFlights();
            assertEquals(expected, flights);
            verify(flightRepository).findAll();

    }

    @Test
    public void shouldReturnFlightBySourceDestination() {
            List<Flight> flights = new ArrayList<Flight>();
            flights.add(flight);
            when(flightRepository.findBySourceAirportAirportAbvName("SN")).thenReturn(flights);
            when(flightRepository.findByDestAirportAirportAbvName("DN")).thenReturn(flights);
            when(flightRepository.findAll()).thenReturn(flights);
            when(flightRepository.findBySourceAirportAirportAbvNameAndDestAirportAirportAbvName("SN", "DN")).thenReturn(flights);

            List<Flight> expected1 = flightService.getFlights(null, null);
            List<Flight> expected2 = flightService.getFlights("SN", null);
            List<Flight> expected3 = flightService.getFlights(null, "DN");
            List<Flight> expected4 = flightService.getFlights("SN", "DN");
            assertEquals(expected1, flights);
            assertEquals(expected2, flights);
            assertEquals(expected3, flights);
            assertEquals(expected4, flights);
            
            verify(flightRepository).findBySourceAirportAirportAbvName("SN");
            verify(flightRepository).findByDestAirportAirportAbvName("DN");
            verify(flightRepository).findAll();
            verify(flightRepository).findBySourceAirportAirportAbvNameAndDestAirportAirportAbvName("SN", "DN");

    }

    @Test
    public void whenGivenId_shouldDeleteFlight_ifFound(){
        when(bookingRepository.deleteByFlight(flight)).thenReturn((long) 0);
        when(flightRepository.deleteByFlightId(flight.getFlightId())).thenReturn(1);
        flightService.deleteFlightById(flight.getFlightId());
        verify(flightRepository).deleteByFlightId(flight.getFlightId());
    }

    @Test
    public void createFlight(){
        when(flightRepository.save(flight)).thenReturn(flight);
        Flight saved = flightService.save(flight);
        assert(saved != null);
        verify(flightRepository).save(flight);

    }

}