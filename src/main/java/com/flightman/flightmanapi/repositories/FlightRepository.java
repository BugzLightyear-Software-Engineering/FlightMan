package com.flightman.flightmanapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightman.flightmanapi.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAll();
    List<Flight> findBySourceAirportId(UUID sourceAirportId);
    List<Flight> findByDestAirportId(UUID destAirportId);
    List<Flight> findBySourceAirportIdAndDestAirportId(UUID sourceAirportId, UUID destAirportId);
}
