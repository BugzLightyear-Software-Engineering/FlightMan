package com.flightman.flightmanapi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.services.FlightModelService;

@RestController
public class FlightModelController {
    
    @Autowired
    private FlightModelService flightModelService;

    /*
     * Method to get all the flight Models in the database if there are any
     * If no data, then we return NO_CONTENT
    */
    @GetMapping("/models")
    public ResponseEntity<List<FlightModel>> getFlightModels(){

        try {
            List<FlightModel> flightModelList = new ArrayList<FlightModel>();
            flightModelList = flightModelService.getAllFlightModels();
            if(!flightModelList.isEmpty())
                return new ResponseEntity<>(flightModelList, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } 
        catch (Exception e) {
            e.printStackTrace(new java.io.PrintStream(System.out));
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 
    * Method that creates a new Flight Model in the Flight Model table by supplying the required flight Model object
    * If failure occurs during creation, returns HTTP NO_CONTENT 
    */
    @PostMapping("/model")
    public ResponseEntity<Integer> createFlightModel(@RequestBody FlightModel flightModel)   
    {  
            try {
                    flightModelService.save(flightModel);
                    Integer flightModelId = flightModel.getFlightModelId();
                    return new ResponseEntity<>(flightModelId, HttpStatus.OK);
            } catch (Exception e) {
                    e.printStackTrace(new java.io.PrintStream(System.out));
                    System.out.println(e);
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

     /* 
    * Method that delets a record in the Flight Model table by FlightModelId
    * If failure occurs during deletion, returns HTTP NO_CONTENT 
    */
    @Transactional
    @DeleteMapping("/model/id/{id}")
    public ResponseEntity<Boolean> deleteModelById(@PathVariable("id") Integer id) {
            try {
                    if(this.flightModelService.deleteModelById(id)==1)
                            return new ResponseEntity<>(true, HttpStatus.OK);
                    return new ResponseEntity<>(false, HttpStatus.OK);
            } catch (Exception e) {
                    e.printStackTrace(new java.io.PrintStream(System.out));
                    System.out.println(e);
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
}
