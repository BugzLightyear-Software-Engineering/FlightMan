package com.flightman.flightmanapi.model;

import java.sql.Time;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID flightId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sourceAirportId")
    private Airport sourceAirport;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "destAirportId")
    private Airport destAirport;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "flightModelId")
    private FlightModel flightModel;

    @NotNull
    @Column(name = "departureTime")
    private Time departureTime;

    @NotNull
    @Column(name = "estArrivalTime")
    private Time estArrivalTime;

    @NotNull
    @Column(name = "numAvailableSeats")
    private Integer numAvailableSeats;

    @Column(name = "delayTime")
    private Time delayTime;

    @Column(name = "cost")
    private int cost;

    public Flight(){};

    public Flight(Airport sourceAirport, Airport destAirport, FlightModel flightModel, Time departureTime, Time estArrivalTime,
                        Integer numAvailableSeats, Time delayTime, int cost) {
		this.sourceAirport = sourceAirport;
        this.destAirport = destAirport;
        this.flightModel = flightModel;
        this.departureTime = departureTime;
        this.estArrivalTime = estArrivalTime;
        this.numAvailableSeats = numAvailableSeats;
        this.delayTime = delayTime;
        this.cost = cost;
	}

    public UUID getFlightId() {
        return flightId;
    }

    public void setFlightId(UUID flightId) {
        this.flightId = flightId;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Airport getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(Airport sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

    public Airport getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(Airport destAirport) {
        this.destAirport = destAirport;
    }

    public FlightModel getFlightModel() {
        return flightModel;
    }

    public void setFlightModel(FlightModel flightModel) {
        this.flightModel = flightModel;
    }

}

