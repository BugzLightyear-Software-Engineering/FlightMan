package com.flightman.flightmanapi.controller;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.services.FlightService;

@RequestMapping("/api")
@RestController
public class FlightController {
    
    @Autowired
    private FlightService flightService;

    /*
     * Method to get all the flights given the source or/and destination airport abreiviation
     * If, source or destination airport is not in database we return NO_CONTENT
     * If, source or destination airport is not provided we return all flights for the given destination or source airport respectively
     */
    @GetMapping("/flights")
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

    /* 
    * Method that creates a new record in the Flight table by supplying the required flight object
    * If failure occurs during creation, returns HTTP NO_CONTENT 
    */
    @PostMapping("/flight")
    public ResponseEntity<UUID> createFlight(@RequestBody Flight flight)   
    {  
            try {
                    Flight createdFlight = flightService.save(flight);
                    UUID flightId = createdFlight.getFlightId();
                    return new ResponseEntity<>(flightId, HttpStatus.OK);
            } catch (Exception e) {
                    e.printStackTrace(new java.io.PrintStream(System.out));
                    System.out.println(e);
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    /* 
    * Method that delets a record in the Flight table by FlightId
    * If failure occurs during deletion, returns HTTP NO_CONTENT 
    */
    @Transactional
    @DeleteMapping("/flight/id/{id}")
    public ResponseEntity<Boolean> deleteFlightById(@PathVariable("id") UUID id) {
        try {
                if(this.flightService.deleteFlightById(id)==1)
                        return new ResponseEntity<>(true, HttpStatus.OK);
                return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
                e.printStackTrace(new java.io.PrintStream(System.out));
                System.out.println(e);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
