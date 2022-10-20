package com.flightman.flightmanapi.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "flightMeals")
public class FlightMeal  implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "flightId")
    private Flight flight;

    @Id
    @ManyToOne
    @JoinColumn(name = "mealId")
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
