package com.example.app.mapper;

import com.example.app.Algo.AirportBFS;
import com.example.app.Algo.FlightBFS;
import com.example.app.model.Airport;
import com.example.app.model.Flight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperBFS {


    public static AirportBFS createDto(Airport airport){
        return AirportBFS.builder()
                .airportId(airport.getAirportId())
                .airportName(airport.getAirportName())
                .build();
    }


    public static Airport createEntity(AirportBFS airportBFS){
        Airport airport = new Airport();
        airport.setAirportName(airportBFS.getAirportName());
        airport.setAirportId(airportBFS.getAirportId());
        return airport;
    }

    public static FlightBFS createDto(Flight flight,List<Airport> airports){
        AirportBFS from = MapperBFS.createDto(airports.stream()
                .filter(airport -> airport.getAirportId()
                        .equals(flight.getFromAirportId())).findFirst().get());
        AirportBFS to = MapperBFS.createDto(airports.stream()
                .filter(airport -> airport.getAirportId()
                        .equals(flight.getToAirportId())).findFirst().get());

        return FlightBFS.builder()
                .from(from)
                .to(to)
                .freeSeats(flight.getFreeSeats())
                .build();
    }


}
