package com.flightman.flightmanapi.model;

import java.util.UUID;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "airport")
@Getter
@Setter
@NoArgsConstructor
public class Airport {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @ApiModelProperty(notes = "Unique identifier of the airport", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
        private UUID airportId;

        @Column(name = "airportName")
        @ApiModelProperty(notes = "Name of the airport", example = "Chennai International Airport")
        private String airportName;

        @Column(name = "airportAbvName", unique = true)
        @ApiModelProperty(notes = "3 character abbreviation of the airport", example = "JFK")
        private String airportAbvName;

        @Column(name = "latitude")
        @ApiModelProperty(notes = "Latitude of the airport", example = "1.1234")
        private String latitude;

        @Column(name = "longitude")
        @ApiModelProperty(notes = "Longitude of the airport", example = "2.2221")
        private String longitude;

        public Airport(String airportName, String airportAbvName, String latitude, String longitude) {
                this.airportName = airportName;
                this.airportAbvName = airportAbvName;
                this.latitude = latitude;
                this.longitude = longitude;
        }
}
