package com.flightman.flightmanapi.controller;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.services.FlightService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api")
@RestController
@Api("Set of endpoints for Creating, Finding, and Deleting Flights.")
public class FlightController {
    
    @Autowired
    private FlightService flightService;

    @ApiOperation(value = "Get flight by Source or/and Destination", notes = "Finds the flights connecting a source and destination airport")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully found the flights"), 
                   @ApiResponse(code = 204, message = "If source or destination airport is not in database"), 
                   @ApiResponse(code = 500, message = "If any other error occurs")})
    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getFlightsBySourceDest(
        @ApiParam(name = "Source Abbreviation", value = "Abbreviation of the Source Airport") @RequestParam(required = false) String sourceAbv, 
        @ApiParam(name = "Destination Abbreviation", value = "Abbreviation of the Destination Airport") @RequestParam(required = false) String destAbv){

        try {
            List<Flight> flightList = flightService.getFlights(sourceAbv, destAbv);
            if(!flightList.isEmpty())
                return new ResponseEntity<>(flightList, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } 
        catch (Exception e) {
            e.printStackTrace(new java.io.PrintStream(System.err));
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Create flight", notes = "Takes in the details of the flights and creates a new flight in the database")
    @ApiResponses({@ApiResponse(code = 200, message = "Flight is successfully created"),
                   @ApiResponse(code = 500, message = "If any other error occurs")})
    @PostMapping("/flight")
    public ResponseEntity<UUID> createFlight(@RequestBody Flight flight)   
    {  
            try {
                    Flight createdFlight = flightService.save(flight);
                    UUID flightId = createdFlight.getFlightId();
                    return new ResponseEntity<>(flightId, HttpStatus.OK);
            } catch (Exception e) {
                    e.printStackTrace(new java.io.PrintStream(System.err));
                    System.err.println(e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @ApiOperation(value = "Create flight", notes = "Takes in the details of the flights and creates a new flight in the database")
    @ApiResponses({@ApiResponse(code = 200, message = "Flight is successfully created"),
                   @ApiResponse(code = 500, message = "If any other error occurs")})
    @PutMapping("/flight/id/{flightID}")
    public ResponseEntity<UUID> updateFlight(@PathVariable UUID flightID, @RequestParam(required = false) Time departureTime, @RequestParam(required = false) Time estArrivalTime, @RequestParam(required = false) Integer flightModelID )   
    {  
            try {
                    Flight updatedFlight = flightService.update(flightID, departureTime, estArrivalTime, flightModelID);
                    UUID flightId = updatedFlight.getFlightId();
                    return new ResponseEntity<>(flightId, HttpStatus.OK);
            } catch (Exception e) {
                    e.printStackTrace(new java.io.PrintStream(System.err));
                    System.err.println(e);
                    return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @ApiOperation(value = "Delete flight", notes = "Deletes a flight by the given Flight Id")
    @ApiResponses({@ApiResponse(code = 200, message = "Flight is successfully deleted"),
                   @ApiResponse(code = 500, message = "If any other error occurs")})
    @Transactional
    @DeleteMapping("/flight/id/{id}")
    public ResponseEntity<Boolean> deleteFlightById(@ApiParam(name = "Id", value = "Id of the flight to be deleted") @PathVariable("id") UUID id) {
        try {
                if(this.flightService.deleteFlightById(id)==1)
                    return new ResponseEntity<>(true, HttpStatus.OK);
                return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
                e.printStackTrace(new java.io.PrintStream(System.err));
                System.err.println(e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
