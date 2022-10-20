package com.flightman.flightmanapi.model;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "airport")
public class Airport {
        
        @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID airportId;

	@Column(name = "airportName")
        private String airportName;
        
        @Column(name = "airportAbvName")
        private String airportAbvName;

        @Column(name = "latitude")
	private String latitude;

        @Column(name = "longitude")
	private String longitude;

        public Airport(){};

        public Airport(String airportName, String airportAbvName, String latitude, String longitude) {
		this.airportName = airportName;
		this.airportAbvName = airportAbvName;
		this.latitude = latitude;
                this.longitude = longitude;
	}

        public String getAirportName() {
                return this.airportName;
        }

        public void setAirportName(String airportName) {
                this.airportName = airportName;
        }

        public String getAirportAbvName() {
                return this.airportAbvName;
        }

        public void setAirportAbvName(String airportAbvName) {
                this.airportAbvName = airportAbvName;
        }

        public String getLatitude() {
                return this.latitude;
        }

        public void setLatitude(String latitude) {
                this.latitude = latitude;
        }

        public String getLongitude() {
                return this.longitude;
        }

        public void setLongitude(String longitude) {
                this.longitude = longitude;
        }

        public UUID getAirportId() {
                return airportId;
        }
}
