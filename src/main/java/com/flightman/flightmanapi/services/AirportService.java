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

        /*
         * Method that returns a list of all airports if airport name is not supplied,
         * else returns airports whose name contains the supplied string.
         */
        public List<Airport> find(String airportName) {
                List<Airport> airportsList = new ArrayList<Airport>();
                if (airportName == null)
                        this.airportRepository.findAll().forEach(airportsList::add);
                else
                        this.airportRepository.findByAirportNameContaining(airportName).forEach(airportsList::add);
                return airportsList;
        }

        /*
         * Method that saves an Airport object to the database.
         */
        public Boolean saveAirport(Airport airport) {
                this.airportRepository.save(airport);
                return true;
        }
}
