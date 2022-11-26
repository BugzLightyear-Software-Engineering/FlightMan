package com.flightman.flightmanapi.model;

import java.sql.Time;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "flights")
@ApiModel(description = "Class representing a flight in the system")
public class Flight {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @ApiModelProperty(notes = "Unique identifier of the flight", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
        private UUID flightId;

        @NotNull
        @ManyToOne
        @JoinColumn(name = "sourceAirportId")
        @ApiModelProperty(notes = "Source Airport Object")
        private Airport sourceAirport;

        @NotNull
        @ManyToOne
        @JoinColumn(name = "destAirportId")
        @ApiModelProperty(notes = "Destination Airport Object")
        private Airport destAirport;

        @NotNull
        @ManyToOne
        @JoinColumn(name = "flightModelId")
        @ApiModelProperty(notes = "Flight Model Object")
        private FlightModel flightModel;

        @NotNull
        @Column(name = "departureTime")
        @ApiModelProperty(notes = "The time the flight departs")
        private Time departureTime;

        @ApiModelProperty(notes = "The time the flight is estimated to arrive by")
        @NotNull
        @Column(name = "estArrivalTime")
        private Time estArrivalTime;

        @Column(name = "delayTime")
        @ApiModelProperty(notes = "The time the flight might be delayed by")
        private Time delayTime;

        public Flight() {
        };

        public Flight(Airport sourceAirport, Airport destAirport, FlightModel flightModel, Time departureTime,
                        Time estArrivalTime, Time delayTime) {
                this.sourceAirport = sourceAirport;
                this.destAirport = destAirport;
                this.flightModel = flightModel;
                this.departureTime = departureTime;
                this.estArrivalTime = estArrivalTime;
                this.delayTime = delayTime;
        }

        public UUID getFlightId() {
                return flightId;
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

        public Integer getNumSeats() {
                return this.flightModel.getSeatCapacity();
        }

        public void setNumSeats(Integer numSeats) {
                this.flightModel.setSeatCapacity(numSeats);
        }

        public Time getDelayTime() {
                return delayTime;
        }

        public void setDelayTime(Time delayTime) {
                this.delayTime = delayTime;
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
