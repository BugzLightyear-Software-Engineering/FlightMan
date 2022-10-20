package com.flightman.flightmanapi.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.services.FlightService;

@RestController
@RequestMapping("/flights")
public class FlightController {
    
    @Autowired
    private FlightService flightService;

    @GetMapping("/sourcedest")
    public ResponseEntity<List<Flight>> getFlightsBySourceDest(@RequestParam(required = false) String sourceAbv, @RequestParam(required = false) String destAbv){

        try {
            List<Flight> flightList = new ArrayList<Flight>();
            flightList = flightService.getFlights(sourceAbv, destAbv);
            if(!flightList.isEmpty())
                return new ResponseEntity<>(flightList, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } 
        catch (Exception e) {
            e.printStackTrace(new java.io.PrintStream(System.out));
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
