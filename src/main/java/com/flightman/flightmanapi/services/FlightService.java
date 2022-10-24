package com.flightman.flightmanapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.repositories.FlightRepository;

@Service
public class FlightService {
    @Autowired 
    private FlightRepository flightRepository;
    
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

    public Integer deleteFlightById(UUID id) {
        return this.flightRepository.deleteByFlightId(id);
    }
}
