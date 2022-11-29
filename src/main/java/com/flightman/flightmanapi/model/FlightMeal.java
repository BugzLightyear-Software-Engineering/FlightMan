package com.flightman.flightmanapi.model;

import java.io.Serializable;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "flightMeals")
@ApiModel(description = "Class representing a flight meal in the system")
@Getter
@Setter
@NoArgsConstructor
public class FlightMeal implements Serializable {
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

        public FlightMeal(Flight flight, Meal meal) {
                this.flight = flight;
                this.meal = meal;
        }
}
