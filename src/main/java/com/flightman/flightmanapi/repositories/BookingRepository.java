package com.flightman.flightmanapi.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightman.flightmanapi.model.Booking;
import com.flightman.flightmanapi.model.Flight;
import com.flightman.flightmanapi.model.User;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
        List<Booking> findByUser(User user);
        List<Booking> findByFlightAndFlightDate(Flight f, Date d);
        Booking findByBookingId(UUID id);

}
