package com.flightman.flightmanapi.model;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user", schema = "public")
@ApiModel(description = "Class representing a user in the system")
@Getter
@Setter
@NoArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "user_id")
        @ApiModelProperty(notes = "Unique identifier of the user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
        private UUID userId;

        @NotNull
        @Column(name = "first_name")
        @ApiModelProperty(notes = "User's first name")
        private String firstName;

        @NotNull
        @Column(name = "last_name")
        @ApiModelProperty(notes = "User's last name")
        private String lastName;

        @NotNull
        @Column(name = "phone_number")
        @ApiModelProperty(notes = "User's phone number")
        private String phoneNumber;

        @NotNull
        @Column(name = "email", unique = true)
        @ApiModelProperty(notes = "User's email")
        private String email;

        @NotNull
        @Column(name = "passport_number")
        @ApiModelProperty(notes = "User's passport number")
        private String passportNumber;

        @NotNull
        @Column(name = "address")
        @ApiModelProperty(notes = "User's address")
        private String address;

        // @NotNull
        @Column(name = "diet_id")
        @ApiModelProperty(notes = "What is the associated diet identifier for this user")
        private Integer diet;

        @NotNull
        @Column(name = "rewards_miles")
        @ApiModelProperty(notes = "How many reward points/miles does this user have")
        private int rewardsMiles;

        public User(final String firstName, final String lastName, final String phoneNumber, final String email,
                        final String passportNumber, final String address, final Integer diet, final int rewardsMiles) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.phoneNumber = phoneNumber;
                this.email = email;
                this.passportNumber = passportNumber;
                this.address = address;
                this.diet = diet;
                this.rewardsMiles = rewardsMiles;
        }
}
