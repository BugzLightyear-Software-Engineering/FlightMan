package com.flightman.flightmanapi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flightman.flightmanapi.model.Airport;
import com.flightman.flightmanapi.services.AirportService;
import com.flightman.flightmanapi.utils.ClassToJsonString;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api")
@RestController
public class AirportController {

        @Autowired
        private AirportService airportService;

        private static final Logger logger = LogManager.getLogger(AirportController.class);

        /*
         * Method that returns a list of all airports in the database if any are
         * available, else returns HTTP NO_CONTENT
         */
        @ApiOperation(value = "Get Airports", notes = "Returns the details of the supplied airport name (if it exists)")
        @ApiResponses({ @ApiResponse(code = 200, message = "Airport details are successfully retrieved"),
                        @ApiResponse(code = 400, message = "The supplied airport name was not found on the server"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during airport detail retrieval") })
        @GetMapping("/airports")
        public ResponseEntity<String> getAirports(@RequestParam(required = false) String airportName)
                        throws JsonProcessingException {
                List<Airport> airportsList;
                airportsList = airportService.find(airportName);
                if (!airportsList.isEmpty()) {
                        final HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                        ClassToJsonString cls = new ClassToJsonString(airportsList);
                        return new ResponseEntity<>(cls.getJsonString(), httpHeaders,
                                        HttpStatus.OK);

                }
                return new ResponseEntity<>("Airport does not exist.", HttpStatus.OK);
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
        public ResponseEntity<String> createAirport(@RequestBody Airport airport) {
                if (Float.valueOf(airport.getLatitude()) < -90 || Float.valueOf(airport.getLongitude()) > 90
                                || Float.valueOf(airport.getLongitude()) < -180
                                || Float.valueOf(airport.getLongitude()) > 180) {
                        return new ResponseEntity<>("Invalid latitude/longitude", HttpStatus.BAD_REQUEST);
                }
                if (airport.getAirportAbvName().length() != 3) {
                        return new ResponseEntity<>("Airport ABV Name must be exactly 3 characters",
                                        HttpStatus.BAD_REQUEST);
                }
                Boolean isAirportReturned = this.airportService.saveAirport(airport);
                if (Boolean.FALSE.equals(isAirportReturned)) {
                        logger.error("Could not create airport!");
                        return new ResponseEntity<>("Could not create airport",
                                        HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>("Airport successfully created", HttpStatus.CREATED);
        }
}
