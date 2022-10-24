package com.flightman.flightmanapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightman.flightmanapi.model.FlightModel;


@Repository
public interface FlightModelRepository extends JpaRepository<FlightModel, Long>{
    List<FlightModel> findAll();
    
    @Modifying
	@Query(value = "DELETE FROM FlightModel WHERE flightModelId = :id")
	Integer deleteByFlightModelId(@Param("id") Integer id);

    FlightModel findByFlightModelId(Integer flightModelId);

}
