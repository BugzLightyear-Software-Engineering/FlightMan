package com.flightman.flightmanapi.model;

import java.util.UUID;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "luggage")
@Getter
@Setter
@NoArgsConstructor
public class Luggage {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @ApiModelProperty(notes = "Unique identifier of the luggage")
        private UUID luggageId;

        @Column(name = "count")
        @ApiModelProperty(notes = "Number of luggage items present")
        private Integer count;

        @Column(name = "total_weight")
        @ApiModelProperty(notes = "Total weight of all the  bags")
        private float weight;

        public Luggage(Integer count, float weight) {
                this.count = count;
                this.weight = weight;
        }
}
