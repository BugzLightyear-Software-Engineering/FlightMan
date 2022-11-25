package com.flightman.flightmanapi.controller;

import java.util.ArrayList;
import java.util.List;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api")
@RestController
public class AirportController {

        @Autowired
        private AirportService airportService;

        /*
         * Method that returns a list of all airports in the database if any are
         * available, else returns HTTP NO_CONTENT
         */
        @ApiOperation(value = "Get Airports", notes = "Returns the details of the supplied airport name (if it exists)")
        @ApiResponses({ @ApiResponse(code = 200, message = "Airport details are successfully retrieved"),
                        @ApiResponse(code = 400, message = "The supplied airport name was not found on the server"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during airport detail retrieval") })
        @GetMapping("/airports")
        public ResponseEntity<?> getAirports(@RequestParam(required = false) String airportName) {
                try {
                        List<Airport> airportsList = new ArrayList<Airport>();
                        airportsList = airportService.find(airportName);
                        if (!airportsList.isEmpty())
                                return new ResponseEntity<>(airportsList, HttpStatus.OK);
                        return new ResponseEntity<>("Airport does not exist.", HttpStatus.BAD_REQUEST);
                } catch (Exception e) {
                        System.err.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        /*
         * Method that creates a new record in the database from the supplied Airport
         * object.
         * If failure occurs during creation, returns HTTP NO_CONTENT
         */
        @ApiOperation(value = "Create Airport", notes = "Takes in the details of the airport and creates a new airport in the database")
        @ApiResponses({ @ApiResponse(code = 200, message = "Airport is successfully created"),
                        @ApiResponse(code = 400, message = "The supplied parameters are invalid and the airport cannot be created"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during the creation of airport") })
        @PostMapping("/airports")
        public ResponseEntity<?> createAirport(@RequestBody Airport airport) {
                System.out.println("HOHOHO");
                // System.out.println(airportName);
                // System.out.println(Float.valueOf(latitude));
                if (Float.valueOf(airport.getLatitude()) < -90 || Float.valueOf(airport.getLongitude()) > 90
                                || Float.valueOf(airport.getLongitude()) < -180
                                || Float.valueOf(airport.getLongitude()) > 180) {
                        System.out.println("LATLONG");
                        return new ResponseEntity<>("Invalid latitude/longitude", HttpStatus.BAD_REQUEST);
                }
                if (airport.getAirportAbvName().length() != 3) {
                        System.out.println("ABVNAME");
                        return new ResponseEntity<>("Airport ABV Name must be exactly 3 characters",
                                        HttpStatus.BAD_REQUEST);
                }
                System.out.println("ELO");
                try {
                        if (this.airportService.saveAirport(airport)) {
                                return new ResponseEntity<>("Airport successfully created", HttpStatus.OK);
                        }
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}
