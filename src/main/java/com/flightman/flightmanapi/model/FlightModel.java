package com.flightman.flightmanapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "flightModel")
public class FlightModel{
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer flightModelId;

    @NotNull
    @Column(name = "flightManufacturerName")
    private String flightManufacturerName;

    @NotNull
    @Column(name = "flightModelNumber")
    private String flightModelNumber;

    @NotNull
    @Column(name = "seatCapacity")
    private Integer seatCapacity;

    @NotNull
    @Column(name = "seatRowCount")
    private Integer seatRowCount;

    @NotNull
    @Column(name = "seatColCount")
    private Integer seatColCount;

    public FlightModel(){}

    public FlightModel(String flightManufacturerName, String flightModelNumber, Integer seatCapacity, Integer seatRowCount, Integer seatColCount){
        this.flightManufacturerName = flightManufacturerName;
        this.flightModelNumber = flightModelNumber;
        this.seatCapacity = seatCapacity;
        this.seatRowCount = seatRowCount;
        this.seatColCount = seatColCount;
    }

    public Integer getFlightModelId() {
        return flightModelId;
    }

    public void setFlightModelId(Integer flightModelId) {
        this.flightModelId = flightModelId;
    }

    public String getFlightManufacturerName() {
        return flightManufacturerName;
    }

    public void setFlightManufacturerName(String flightManufacturerName) {
        this.flightManufacturerName = flightManufacturerName;
    }

    public String getFlightModelNumber() {
        return flightModelNumber;
    }

    public void setFlightModelNumber(String flightModelNumber) {
        this.flightModelNumber = flightModelNumber;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Integer getSeatRowCount() {
        return seatRowCount;
    }

    public void setSeatRowCount(Integer seatRowCount) {
        this.seatRowCount = seatRowCount;
    }

    public Integer getSeatColCount() {
        return seatColCount;
    }

    public void setSeatColCount(Integer seatColCount) {
        this.seatColCount = seatColCount;
    }

    
   

    
}
