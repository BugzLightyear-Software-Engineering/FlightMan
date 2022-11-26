package com.flightman.flightmanapi.services;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.repositories.BookingRepository;
import com.flightman.flightmanapi.repositories.FlightModelRepository;
import com.flightman.flightmanapi.repositories.FlightRepository;

@Service
public class FlightService {
    @Autowired 
    private FlightRepository flightRepository;

    @Autowired
    private FlightModelRepository flightModelRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    public List<Flight> getAllFlights() {
        return (List<Flight>) flightRepository.findAll();
    }

    public List<Flight> getFlights(String sourceAbv, String destAbv){
        List<Flight> flightList = new ArrayList<Flight>();

        if(sourceAbv == null && destAbv == null)
            flightList = getAllFlights();
    
        else if ( destAbv == null){
            flightList = flightRepository.findBySourceAirportAirportAbvName(sourceAbv);
        }

        else if ( sourceAbv == null){
            flightList = flightRepository.findByDestAirportAirportAbvName(destAbv);
        }
 
        else{
            flightList = flightRepository.findBySourceAirportAirportAbvNameAndDestAirportAirportAbvName(sourceAbv, destAbv);
        }
        return flightList;
    }

    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight update(UUID flightId, Time departureTime, Time estArrivalTime, Integer flightModelId) {
        Flight f = flightRepository.findByFlightId(flightId);
        if(departureTime != null){
            f.setDepartureTime(departureTime);
        }
        if(estArrivalTime != null){
            f.setEstArrivalTime(estArrivalTime);
        }
        System.out.println(flightModelId);
        if(flightModelId != null){
            FlightModel m = flightModelRepository.findByFlightModelId(flightModelId);
            System.out.println(m);
            if(m != null){
                f.setFlightModel(m);
            }
        }
        return flightRepository.save(f);
    }

    public Integer deleteFlightById(UUID id) {
        Flight f = this.flightRepository.findByFlightId(id);
        this.bookingRepository.deleteByFlight(f);
        return this.flightRepository.deleteByFlightId(id);
    }
}
