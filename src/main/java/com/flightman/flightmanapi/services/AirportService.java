package com.flightman.flightmanapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.repositories.AirportRepository;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    public List<Airport> list(String airportName) {
        List<Airport> airportsList = new ArrayList<Airport>();
        // airportRepository = new AirportRepository();
        if (airportName == null)
                this.airportRepository.findAll().forEach(airportsList::add);
        else
                this.airportRepository.findByAirportNameContaining(airportName).forEach(airportsList::add);
        return airportsList;
    }
}
