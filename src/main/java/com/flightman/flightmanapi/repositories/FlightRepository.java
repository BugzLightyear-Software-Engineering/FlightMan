package com.flightman.flightmanapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightman.flightmanapi.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAll();
    Flight findByFlightId(UUID flightId);
    List<Flight> findBySourceAirportAirportAbvName(String sourceAbvName);
    List<Flight> findByDestAirportAirportAbvName(String destAbvName);
    List<Flight> findBySourceAirportAirportAbvNameAndDestAirportAirportAbvName(String sourceAbvName, String destAbvName);

    Integer deleteByFlightId(@Param("id") UUID id);
}
