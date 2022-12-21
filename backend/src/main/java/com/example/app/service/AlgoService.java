package com.example.app.service;

import com.example.app.Algo.AirportBFS;
import com.example.app.Algo.BFS;
import com.example.app.Algo.FlightBFS;
import com.example.app.mapper.MapperBFS;
import com.example.app.model.Airport;
import com.example.app.model.Flight;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class AlgoService {

    private final FlightService flightService;
    private final AirportService airportService;

    public void addFlight(Flight flight){
      //  BFS.getInMemoryFlights().add(MapperBFS.createFlightBFS(flight));
    }

    public void addAirport(Airport airp){
        BFS.getInMemoryAirports().add(MapperBFS.createAirportBFS(airp));
    }

    public void removeFlight(Flight flight){
//        FlightBFS flightBFS = MapperBFS.createFlightBFS(flight);
//        BFS.getInMemoryFlights().remove(flightBFS);
    }

    /**
     * All flights flying from or to the airport to be deleted will be deleted as well.
     */
    public void removeAirport(Airport airp){
        AirportBFS airportBFS = MapperBFS.createAirportBFS(airp);
        BFS.getInMemoryAirports().remove(airportBFS);
        BFS.getInMemoryFlights().stream()
                .forEach(flight -> {
                    if(flight.getFrom().equals(airportBFS.getAirportId())
                    || flight.getTo().equals(airportBFS.getAirportId())){
                        BFS.getInMemoryFlights().remove(flight);
                    }
                });
    }

    /**
     * Fills inmemory DB of Algo, so tht we don't have to refetch all the data
     * from database with frequent calls of Algo.
     * Usage: At the beginnig only, later unnecessarily slow.
     */
    public void loadFlights(){
//        List<FlightBFS> flights = new LinkedList<>();
//        flightService.getAllFlights().stream()
//                .forEach( flight -> {
//                    flights.add(MapperBFS.createFlightBFS(flight));
//                });
//
//        List<AirportBFS> airports = new ArrayList<>();
//        airportService.getAll().stream()
//                .forEach(airport -> {
//                    airports.add(MapperBFS.createAirportBFS(airport));
//                });
    }


}
