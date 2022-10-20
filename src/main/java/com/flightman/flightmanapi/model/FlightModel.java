package com.flightman.flightmanapi.model;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "flightModel")
public class FlightModel{
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID flightModelId;

    @Column(name = "modelName")
    private String modelName;

    @Column(name = "modelNumber")
    private Integer modelNumber;

    @Column(name = "seatCapacity")
    private Integer seatCapacity;

    @Column(name = "seatRowCount")
    private Integer seatRowCount;

    @Column(name = "seatColCount")
    private Integer seatColCount;

    public FlightModel(){}

    public FlightModel(String modelName, Integer modelNumber, Integer seatCapacity, Integer seatRowCount, Integer seatColCount){
        this.modelName = modelName;
        this.modelNumber = modelNumber;
        this.seatCapacity = seatCapacity;
        this.seatRowCount = seatRowCount;
        this.seatColCount = seatColCount;
    }

    public UUID getFlightModelId() {
        return flightModelId;
    }

    public void setFlightModelId(UUID flightModelId) {
        this.flightModelId = flightModelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(Integer modelNumber) {
        this.modelNumber = modelNumber;
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
    };

    
}
