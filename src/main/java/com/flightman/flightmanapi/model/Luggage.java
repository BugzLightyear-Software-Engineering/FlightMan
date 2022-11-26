package com.flightman.flightmanapi.model;

import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "luggage")
public class Luggage {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID luggageId;

        @Column(name = "count")
        private Integer count;

        @Column(name = "total_weight")
        private float weight;

        public Integer getCount() {
                return this.count;
        }

        public void setCount(Integer count) {
                this.count = count;
        }

        public float getWeight() {
                return this.weight;
        }

        public void setWeight(float weight) {
                this.weight = weight;
        }

        public Luggage() {
        }

        public Luggage(Integer count, float weight) {
                this.count = count;
                this.weight = weight;
        }
}
