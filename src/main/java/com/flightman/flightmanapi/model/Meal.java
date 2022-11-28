package com.flightman.flightmanapi.model;

import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor
public class Meal {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @ApiModelProperty(notes = "Unique identifier of the meal")
        private UUID mealId;

        @NotNull
        @Column(name = "mealName")
        @ApiModelProperty(notes = "Name of the meal")
        private String mealName;

        @NotNull
        @Column(name = "mealDesc")
        @ApiModelProperty(notes = "Description of the meal")
        private String mealDesc;

        public Meal(String mealName, String mealDesc) {
                this.mealName = mealName;
                this.mealDesc = mealDesc;
        }
}
