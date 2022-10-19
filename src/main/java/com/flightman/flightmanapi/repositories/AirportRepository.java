package com.flightman.flightmanapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightman.flightmanapi.model.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
  List<Airport> findByAirportNameContaining(String airportName);
}