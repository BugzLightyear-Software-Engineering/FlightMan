package com.flightman.flightmanapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.services.AirportService;

@RequestMapping("/api")
@RestController
public class AirportController {

        @Autowired
        private AirportService airportService;

        /* 
         * Method that returns a list of all airports in the database if any are available, else returns HTTP NO_CONTENT 
        */
	@GetMapping("/airports")
	public ResponseEntity<List<Airport>> getAirports(@RequestParam(required = false) String airportName) {
		try {
                        List<Airport> airportsList = new ArrayList<Airport>();
                        airportsList = airportService.find(airportName);
                        if(!airportsList.isEmpty())
                                return new ResponseEntity<>(airportsList, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

        /* 
         * Method that creates a new record in the database from the supplied Airport object. 
         * If failure occurs during creation, returns HTTP NO_CONTENT 
        */
        @PostMapping("/airports")
        public ResponseEntity<UUID> createUser(@RequestBody Airport airport)   
        {  
                try {
                        if(this.airportService.saveAirport(airport)){
                                return new ResponseEntity<>(airport.getAirportId(), HttpStatus.OK);
                        }
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}
