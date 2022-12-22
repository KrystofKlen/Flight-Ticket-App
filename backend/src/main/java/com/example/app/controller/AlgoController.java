package com.example.app.controller;

import com.beust.ah.A;
import com.example.app.Algo.AirportBFS;
import com.example.app.Algo.BFS;
import com.example.app.dto.AirportDto;
import com.example.app.dto.FlightDto;
import com.example.app.mapper.MapperBFS;
import com.example.app.mapper.MapperDto;
import com.example.app.model.Airport;
import com.example.app.model.Flight;
import com.example.app.service.AirportService;
import com.example.app.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/algo")
@AllArgsConstructor
@CrossOrigin
public class AlgoController {
    private final AirportService airportService;
    private final FlightService flightService;
    @GetMapping("/{fromS}/{toS}")
    public ResponseEntity<List<Flight>> getFromTo(@PathVariable String fromS, @PathVariable String toS){
        fromS = fromS.replaceAll("%20"," ");
        toS = toS.replaceAll("%20"," ");

        Optional<Airport> optionalAirportF =  airportService.findByName(fromS);
        Optional<Airport> optionalAirportT = airportService.findByName(toS);
        if(optionalAirportT.isEmpty() || optionalAirportT.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Airport from = optionalAirportF.get();
        Airport to = optionalAirportT.get();

        List<Airport> airports = airportService.getAll();
        List<Flight> flights= flightService.getAllFlights();
        BFS bfs = new BFS();

        List<Long> result = bfs.find(from,to,flights);
        List<Flight> route = new LinkedList<>();
        for(int i = 0; i< (result.size() - 1); i++){
            Long fromId = result.get(i);
            Long toId = result.get(i+1);
            Optional<Airport> f = airportService.findById(fromId);
            Optional<Airport> t = airportService.findById(toId);
            if(f.isEmpty() || t.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Optional<List<Flight>> flight = flightService.findFlightsFromTo(f.get(),t.get());
            if(flight.isEmpty() || flight.get().isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            route.add(flight.get().stream().findFirst().get());
            System.out.println(fromId+" -> "+toId);
        }

        return new ResponseEntity<>(route,HttpStatus.OK);
    }
}
