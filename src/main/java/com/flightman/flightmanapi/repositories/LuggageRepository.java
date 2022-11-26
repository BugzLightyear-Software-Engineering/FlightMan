package com.flightman.flightmanapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightman.flightmanapi.model.Luggage;

@Repository
public interface LuggageRepository extends JpaRepository<Luggage, Long> {

}
