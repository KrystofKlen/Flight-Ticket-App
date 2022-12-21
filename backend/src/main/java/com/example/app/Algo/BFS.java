package com.example.app.Algo;

import com.example.app.model.Airport;
import com.example.app.model.Flight;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class BFS {
    @Getter
    private static Set<FlightBFS> inMemoryFlights;
    @Getter
    private static Set<AirportBFS> inMemoryAirports;

    public void runBFS(AirportBFS from, AirportBFS to){

    }


}
