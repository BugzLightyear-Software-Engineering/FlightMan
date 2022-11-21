package com.flightman.flightmanapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.model.User;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
        List<Booking> findByUser(User user);

        Booking findByBookingId(UUID bookingId);

        @Modifying
		@Query(value = "DELETE FROM Booking WHERE bookingId = :id")
		Integer deleteById(@Param("id") UUID id);
}
