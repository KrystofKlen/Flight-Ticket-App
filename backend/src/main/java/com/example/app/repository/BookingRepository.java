package com.example.app.repository;

import com.example.app.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT * from booking WHERE flight_flight_id = :flightId",nativeQuery = true)
    List<Booking> getBookingsForFlight(@Param("flightId") Long flightId);
}
