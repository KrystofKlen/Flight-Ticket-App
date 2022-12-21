package com.example.app.repository;

import com.example.app.model.Airport;
import com.example.app.model.Flight;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE flight SET free_seats=?2 WHERE flight_id=?1", nativeQuery = true)
    void increaseFreeSeats(Long flightId, Integer freeSeats);


    @Query(value = "SELECT * FROM flight WHERE from_airport_id = ?1 AND to_airport_id = ?2",nativeQuery = true)
    List<Flight> getFlightsFromTo(Long fromId, Long toId);



}
