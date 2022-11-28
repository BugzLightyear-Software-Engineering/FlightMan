package com.flightman.flightmanapi.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "booking")
@ApiModel(description = "Class representing a booking in the system")
@Getter
@Setter
@NoArgsConstructor
public class Booking {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @ApiModelProperty(notes = "Unique identifier of the booking", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
        private UUID bookingId;

        @OneToOne
        @JoinColumn(name = "user_id")
        @ApiModelProperty(notes = "Unique identifier of the user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
        private User user;

        @OneToOne
        @JoinColumn(name = "flight_id")
        @ApiModelProperty(notes = "Unique identifier of the flight", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
        private Flight flight;

        @OneToOne
        @JoinColumn(name = "luggage_id")
        @ApiModelProperty(notes = "Luggage associated with the booking during luggage check-in", required = true)
        private Luggage luggage;

        @Column(name = "seatNumber")
        @ApiModelProperty(notes = "Which seat number is this booking for")
        private String seatNumber;

        @Column(name = "paymentStatus")
        @ApiModelProperty(notes = "Is the payment for this booking complete")
        private Boolean paymentStatus;

        @Column(name = "useRewardPoints")
        @ApiModelProperty(notes = "Were reward points used for this booking")
        private Boolean useRewardPoints;

        @DateTimeFormat(pattern = "MM-dd-yyyy")
        @Column(name = "flight_date")
        @ApiModelProperty(notes = "What is the departure date of this booking")
        private Date flightDate;

        @Column(name = "user_check_in")
        @ApiModelProperty(notes = "Has the user checked in")
        private Boolean userCheckIn;

        public Booking(User user, Flight flight, String seatNumber, Boolean paymentStatus, Boolean useRewardPoints,
                        Date date) {
                this.user = user;
                this.flight = flight;
                this.seatNumber = seatNumber;
                this.paymentStatus = paymentStatus;
                this.useRewardPoints = useRewardPoints;
                this.flightDate = date;
                this.userCheckIn = false;
        }

        public Booking(User user, Flight flight, String seatNumber, Boolean paymentStatus, Boolean useRewardPoints,
                        Date date, Boolean userCheckIn) {
                this.user = user;
                this.flight = flight;
                this.seatNumber = seatNumber;
                this.paymentStatus = paymentStatus;
                this.useRewardPoints = useRewardPoints;
                this.flightDate = date;
                this.userCheckIn = userCheckIn;
        }

}
