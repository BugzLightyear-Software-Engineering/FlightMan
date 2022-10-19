package com.flightman.flightmanapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {
        
        @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "user_id")
	private int id;

        @NotNull
	@Column(name = "user_firstname")
        private String firstName;

        @NotNull
        @Column(name = "user_lastname")
        private String lastName;

        @NotNull
        @Column(name = "phone_number")
        private String phoneNumber;

        @NotNull
        @Column(name = "email", unique = true)
        private String email;

        @NotNull
        @Column(name = "passport_number")
        private String passportNumber;

        @NotNull
        @Column(name = "address")
        private String address;

        @NotNull
        @Column(name = "diet")
        private String diet;

        @NotNull
        @Column(name = "rewards_miles")
        private int rewardsMiles;

        public User(){};

        public User(String firstName, String lastName, String phoneNumber, String email,
                String passportNumber, String address, String diet, int rewardsMiles) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.phoneNumber = phoneNumber;
                this.email = email;
                this.passportNumber = passportNumber;
                this.address = address;
                this.diet = diet;
                this.rewardsMiles = rewardsMiles;
        }

        public int getID() {
                return this.id;
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

        public String getAddress() {
                return this.address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getDiet() {
                return this.diet;
        }

        public void setDiet(String diet) {
                this.diet = diet;
        }

        public int getRewardsMiles() {
                return this.rewardsMiles;
        }

        public void setRewardsMiles(int rewardsMiles) {
                this.rewardsMiles = rewardsMiles;
        }
}
