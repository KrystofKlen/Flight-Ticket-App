package com.example.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Reference;

import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue
    private Long flightId;

    private Integer freeSeats;

    @Reference(Airport.class)
    private Long fromAirportId;
    @Reference(Airport.class)
    private Long toAirportId;

}
