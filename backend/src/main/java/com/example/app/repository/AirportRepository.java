package com.example.app.repository;

import com.example.app.model.Airport;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query(value = "SELECT * FROM airport WHERE airport_name = ?1",nativeQuery = true)
    List<Airport> findByName(String name);

}
