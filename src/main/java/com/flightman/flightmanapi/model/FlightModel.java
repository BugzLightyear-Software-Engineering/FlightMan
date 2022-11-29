package com.flightman.flightmanapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@ApiModel(description = "Class representing a flight model in the system")
@Table(name = "flightModel")
@Getter
@Setter
@NoArgsConstructor
public class FlightModel {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @ApiModelProperty(notes = "The unique auto generated ID")
        private Integer flightModelId;

        @NotNull
        @Column(name = "flightManufacturerName")
        @ApiModelProperty(notes = "Name of the flight manufacturer")
        private String flightManufacturerName;

        @NotNull
        @Column(name = "flightModelNumber")
        @ApiModelProperty(notes = "Flight Model Number")
        private String flightModelNumber;

        @NotNull
        @Column(name = "seatCapacity")
        @ApiModelProperty(notes = "The seat capacity of the flight")
        private Integer seatCapacity;

        @NotNull
        @Column(name = "seatRowCount")
        @ApiModelProperty(notes = "The number of rows of seats")
        private Integer seatRowCount;

        @NotNull
        @Column(name = "seatColCount")
        @ApiModelProperty(notes = "The number of columns of seats")
        private Integer seatColCount;

        public FlightModel(String flightManufacturerName, String flightModelNumber, Integer seatCapacity,
                        Integer seatRowCount, Integer seatColCount) {
                this.flightManufacturerName = flightManufacturerName;
                this.flightModelNumber = flightModelNumber;
                this.seatCapacity = seatCapacity;
                this.seatRowCount = seatRowCount;
                this.seatColCount = seatColCount;
        }
}
