package com.flightman.flightmanapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.repositories.AirportRepository;
import com.flightman.flightmanapi.repositories.FlightRepository;

@Service
public class FlightService {
    @Autowired 
    private FlightRepository flightRepository;
    @Autowired 
    private AirportRepository airportRepository;
    
    public List<Flight> getAllFlights() {
        return (List<Flight>) flightRepository.findAll();
    }

    public List<Flight> getSourceFlights(UUID sourceAirportId){
        return (List<Flight>) flightRepository.findBySourceAirportId(sourceAirportId);
    }

    public List<Flight> getDestFlights(UUID destAirportId){
        return (List<Flight>) flightRepository.findByDestAirportId(destAirportId);
    }

    public List<Flight> getSourceDestFlights(UUID sourceAirportId, UUID destAirportId){
        return (List<Flight>) flightRepository.findBySourceAirportIdAndDestAirportId(sourceAirportId, destAirportId);
    }

    public List<Flight> getFlights(String sourceAbv, String destAbv){
        List<Flight> flightList = new ArrayList<Flight>();
        List<Airport> source = new ArrayList<Airport>();
        List<Airport> dest = new ArrayList<Airport>();

        if(sourceAbv == null && destAbv == null)
            flightList = getAllFlights();
    
        else if ( destAbv == null){
            source= airportRepository.findByAirportAbvName(sourceAbv);
            if(source.size()==0){
                return flightList;
            }
            UUID sourceId = source.get(0).getAirportId();
            System.out.println(sourceId);
            flightList = getSourceFlights(sourceId);
        }

        else if ( sourceAbv == null){
            dest = airportRepository.findByAirportAbvName(destAbv);
            if(dest.size()==0){
                return flightList;
            }
            UUID destId = dest.get(0).getAirportId();
            System.out.println(destId);
            flightList = getDestFlights(destId);
        }   
        else{
            source= airportRepository.findByAirportAbvName(sourceAbv);
            dest = airportRepository.findByAirportAbvName(destAbv);
            if(source.size()==0 || dest.size()==0)
                return flightList;
            UUID destId = dest.get(0).getAirportId();
            UUID sourceId = source.get(0).getAirportId();
            flightList = getSourceDestFlights(sourceId, destId);
        }
        return flightList;
    }
}
