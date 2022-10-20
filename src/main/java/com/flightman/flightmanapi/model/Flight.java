package com.flightman.flightmanapi.model;

import java.sql.Time;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID flightId;

    @Column(name = "sourceAirportId")
    private UUID sourceAirportId;

    @Column(name= "destAirportId")
    private UUID destAirportId;

    @Column(name = "flightModelId")
    private UUID flightModelId;

    @Column(name = "departureTime")
    private Time departureTime;

    @Column(name = "estArrivalTime")
    private Time estArrivalTime;

    @Column(name = "numAvailableSeats")
    private Integer numAvailableSeats;

    @Column(name = "delayTime")
    private Time delayTime;

    public Flight(){};

    public Flight(UUID sourceAirportId, UUID destAirportId, UUID flightModelId, Time departureTime, Time estArrivalTime,
                        Integer numAvailableSeats, Time delayTime) {
		this.sourceAirportId = sourceAirportId;
        this.destAirportId = destAirportId;
        this.flightModelId = flightModelId;
        this.departureTime = departureTime;
        this.estArrivalTime = estArrivalTime;
        this.numAvailableSeats = numAvailableSeats;
        this.delayTime = delayTime;
	}

    public UUID getFlightId() {
        return flightId;
    }

    public void setFlightId(UUID flightId) {
        this.flightId = flightId;
    }

    public UUID getSourceAirportId() {
        return sourceAirportId;
    }

    public void setSourceAirportId(UUID sourceAirportId) {
        this.sourceAirportId = sourceAirportId;
    }

    public UUID getDestAirportId() {
        return destAirportId;
    }

    public void setDestAirportId(UUID destAirportId) {
        this.destAirportId = destAirportId;
    }

    public UUID getFlightModelId() {
        return flightModelId;
    }

    public void setFlightModelId(UUID flightModelId) {
        this.flightModelId = flightModelId;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getEstArrivalTime() {
        return estArrivalTime;
    }

    public void setEstArrivalTime(Time estArrivalTime) {
        this.estArrivalTime = estArrivalTime;
    }

    public Integer getNumAvailableSeats() {
        return numAvailableSeats;
    }

    public void setNumAvailableSeats(Integer numAvailableSeats) {
        this.numAvailableSeats = numAvailableSeats;
    }

    public Time getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Time delayTime) {
        this.delayTime = delayTime;
    }

   

}

