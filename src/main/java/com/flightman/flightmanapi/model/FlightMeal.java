package com.flightman.flightmanapi.model;

import java.io.Serializable;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "flightMeals")
@ApiModel(description = "Class representing a flight meal in the system")
public class FlightMeal  implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "flightId")
    @ApiModelProperty(notes = "The flight object")
    private Flight flight;

    @Id
    @ManyToOne
    @JoinColumn(name = "mealId")
    @ApiModelProperty(notes = "Meal object")
    private Meal meal;

    public FlightMeal(){};

    public FlightMeal(Flight flight, Meal meal){
        this.flight = flight;
        this.meal = meal;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

}
