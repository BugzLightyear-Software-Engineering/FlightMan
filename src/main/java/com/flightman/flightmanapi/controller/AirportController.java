package com.flightman.flightmanapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.services.AirportService;

@RestController
public class AirportController {

        @Autowired
        private AirportService airportService;

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
}
