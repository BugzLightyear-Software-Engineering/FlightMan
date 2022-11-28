package com.flightman.flightmanapi.model;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "user", schema = "public")
@ApiModel(description = "Class representing a user in the system")
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

        @NotNull
        @Column(name = "authorization_level")
        private int authorizationLevel;

        public User(){};

        public User(String firstName, String lastName, String phoneNumber, String email,
                String passportNumber, String address, Integer diet, int rewardsMiles, int authorizationLevel) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.phoneNumber = phoneNumber;
                this.email = email;
                this.passportNumber = passportNumber;
                this.address = address;
                this.diet = diet;
                this.rewardsMiles = rewardsMiles;
                this.authorizationLevel = authorizationLevel;
        }

        public UUID getID() {
                return this.userId;
        }

        public String getFirstName() {
                return this.firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return this.lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getPhoneNumber() {
                return this.phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
                return this.email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassportNumber() {
                return this.passportNumber;
        }

        public void setPassportNumber(String passportNumber) {
                this.passportNumber = passportNumber;
        }

        // public String getAddress() {
        //         return this.address;
        // }

        // public void setAddress(String address) {
        //         this.address = address;
        // }

        public Integer getDiet() {
                return this.diet;
        }

        public void setDiet(Integer diet) {
                this.diet = diet;
        }

        public int getRewardsMiles() {
                return this.rewardsMiles;
        }

        public void setRewardsMiles(int rewardsMiles) {
                this.rewardsMiles = rewardsMiles;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }
        
        public int getAuthorizationLevel() {
                return this.authorizationLevel;
        }

        public void setAuthorizationLevel(int authorizationLevel) {
                this.authorizationLevel = authorizationLevel;
        }
}
