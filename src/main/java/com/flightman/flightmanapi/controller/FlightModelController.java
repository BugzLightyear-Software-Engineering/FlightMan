package com.flightman.flightmanapi.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.services.FlightModelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api")
@RestController
@Api("Set of endpoints for Creating, Finding, and Deleting Flight Models.")
public class FlightModelController {
    
    @Autowired
    private FlightModelService flightModelService;

    private static final Logger logger = LogManager.getLogger(FlightModelController.class);

    /*
     * Method to get all the flight Models in the database if there are any
     * If no data, then we return NO_CONTENT
    */
    @ApiOperation(value = "Get all flight models", notes = "Get all the flight models in the database")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully found the flight models"), 
                   @ApiResponse(code = 204, message = "If no flight models in the data"), 
                   @ApiResponse(code = 500, message = "If any other error occurs")})
    @GetMapping("/models")
    public ResponseEntity<List<FlightModel>> getFlightModels(){

        List<FlightModel> flightModelList = flightModelService.getAllFlightModels();
        if(!flightModelList.isEmpty())
                return new ResponseEntity<>(flightModelList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* 
    * Method that creates a new Flight Model in the Flight Model table by supplying the required flight Model object
    * If failure occurs during creation, returns HTTP NO_CONTENT 
    */
    @ApiOperation(value = "Get create a flight model", notes = "Give the details to create a new flight model in the database")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully created the flight model"), 
                   @ApiResponse(code = 400, message = "If the input parameters are invalid"), 
                   @ApiResponse(code = 500, message = "If any other error occurs")})
    @PostMapping("/model")
    public ResponseEntity<?> createFlightModel(@RequestBody FlightModel flightModel)   
    {  
        if(flightModel.getSeatCapacity() < 0){
                return new ResponseEntity<>("The seat capacity cannot be negative", HttpStatus.BAD_REQUEST);
        }
        if(flightModel.getSeatRowCount() < 0){
                return new ResponseEntity<>("The seat row count cannot be negative", HttpStatus.BAD_REQUEST);
        }
        if(flightModel.getSeatColCount() < 0){
                return new ResponseEntity<>("The seat col count cannot be negative", HttpStatus.BAD_REQUEST);
        }
        try {
                FlightModel flightModelcreated = flightModelService.save(flightModel);
                Integer flightModelId = flightModelcreated.getFlightModelId();
                return new ResponseEntity<>(flightModelId, HttpStatus.OK);
        } catch (Exception e) {
                logger.error(e.getStackTrace());
                logger.error(e);
                return new ResponseEntity<>("Input invalid", HttpStatus.BAD_REQUEST);
        }
    }

     /* 
    * Method that delets a record in the Flight Model table by FlightModelId
    * If failure occurs during deletion, returns HTTP NO_CONTENT 
    */
    @ApiOperation(value = "Delete flight model", notes = "Deletes a flight model by the given Flight Model Id")
    @ApiResponses({@ApiResponse(code = 200, message = "Flight Model is successfully deleted"),
                   @ApiResponse(code = 400, message = "Flight Model doesnt exist"),
                   @ApiResponse(code = 500, message = "If any other error occurs")})
    @Transactional
    @DeleteMapping("/model/id/{id}")
    public ResponseEntity<?> deleteModelById(@PathVariable("id") Integer id) {

        if(this.flightModelService.deleteModelById(id)==1)
                return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>("Invalid Id", HttpStatus.BAD_REQUEST);
    }
}
