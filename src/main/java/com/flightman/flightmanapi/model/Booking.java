package com.flightman.flightmanapi.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "booking")
public class Booking {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @ApiModelProperty(notes = "Unique identifier of the booking", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
        private UUID bookingId;

        @OneToOne
        @JoinColumn(name = "user_id")
        @ApiModelProperty(notes = "User associated with the booking", required = true)
        private User user;

        @OneToOne
        @JoinColumn(name = "flight_id")
        @ApiModelProperty(notes = "Flight associated with the booking", required = true)
        private Flight flight;

        @OneToOne
        @JoinColumn(name = "luggage_id")
        @ApiModelProperty(notes = "Luggage associated with the booking during luggage check-in", required = true)
        private Luggage luggage;

        @Column(name = "seatNumber")
        @ApiModelProperty(notes = "Seat number allocated for this booking", required = true)
        private String seatNumber;

        @Column(name = "paymentStatus")
        @ApiModelProperty(notes = "The payment status for the booking")
        private Boolean paymentStatus;

        @Column(name = "flight_date")
        @ApiModelProperty(notes = "Date on which the flight departs")
        private Date flightDate;

        @Column(name = "user_check_in")
        @ApiModelProperty(notes = "Check-in status of the user")
        private Boolean userCheckIn;

        // TODO : Use libs to generate get/set at runtime - LOMBOK

        public Booking() {
        }

        public Booking(User user, Flight flight, String seatNumber, Date date, Boolean paymentStatus) {
                this.user = user;
                this.flight = flight;
                this.seatNumber = seatNumber;
                this.paymentStatus = paymentStatus;
                this.flightDate = date;
                this.userCheckIn = false;
        }

        public Booking(User user, Flight flight, String seatNumber, Date date, Boolean paymentStatus,
                        Boolean userCheckIn) {
                this.user = user;
                this.flight = flight;
                this.seatNumber = seatNumber;
                this.paymentStatus = paymentStatus;
                this.flightDate = date;
                this.userCheckIn = userCheckIn;
        }

        public UUID getBookingId() {
                return this.bookingId;
        }

        public void setBookingId(UUID bookingId) {
                this.bookingId = bookingId;
        }

        public User getUser() {
                return this.user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public Flight getFlight() {
                return this.flight;
        }

        public void setFlight(Flight flight) {
                this.flight = flight;
        }

        public Luggage getLuggage() {
                return this.luggage;
        }

        public void setLuggage(Luggage luggage) {
                this.luggage = luggage;
        }

        public String getSeatNumber() {
                return this.seatNumber;
        }

        public void setSeatNumber(String seatNumber) {
                this.seatNumber = seatNumber;
        }

        public Boolean getPaymentStatus() {
                return this.paymentStatus;
        }

        public void setPaymentStatus(Boolean paymentStatus) {
                this.paymentStatus = paymentStatus;
        }

        public Date getFlightDate() {
                return this.flightDate;
        }

        public void setFlightDate(Date flightDate) {
                this.flightDate = flightDate;
        }

        public Boolean getUserCheckIn() {
                return userCheckIn;
        }

        public void setUserCheckIn(Boolean userCheckIn) {
                this.userCheckIn = userCheckIn;
        }
}
