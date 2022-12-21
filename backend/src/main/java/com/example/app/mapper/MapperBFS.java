package com.example.app.mapper;

import com.example.app.Algo.AirportBFS;
import com.example.app.Algo.FlightBFS;
import com.example.app.model.Airport;
import com.example.app.model.Flight;

public class MapperBFS {


    public static AirportBFS createAirportBFS(Airport airport){
        return AirportBFS.builder()
                .airportId(airport.getAirportId())
                .build();
    }

}
