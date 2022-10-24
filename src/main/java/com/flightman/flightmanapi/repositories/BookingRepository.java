package com.flightman.flightmanapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.model.User;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
        List<Booking> findByUser(User user);
}
