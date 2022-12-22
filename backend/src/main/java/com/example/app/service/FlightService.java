package com.example.app.service;

import com.example.app.mapper.MapperDto;
import com.example.app.model.Airport;
import com.example.app.model.Flight;
import com.example.app.model.Person;
import com.example.app.repository.FlightRepository;
import com.example.app.repository.PersonRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightService {
    private FlightRepository flightRepository;
    private final PersonRepository personRepository;

    public void addFlight(Flight flight){
        flightRepository.save(flight);
    }
    public void deleteFlight(Long id){
        flightRepository.deleteById(id);
    }

    public Optional<Flight> getFlight(Long flightId){
        return flightRepository.findById(flightId);
    }

    /**
     *
     * @param flightId
     * @param additiveConstant
     * @return true = ok, false = something went wrong (flight does not exist, or number can not be decreased)
     */
    public boolean updateFreeSeats(Long flightId, Integer additiveConstant){
        Optional<Flight> optFlight = getFlight(flightId);
        if(optFlight.isEmpty()) return false;
        Flight flight = optFlight.get();
        Integer freeSeats = flight.getFreeSeats() + additiveConstant;
        if(freeSeats == 0){
            return false;
        }
        flightRepository.increaseFreeSeats(flightId,freeSeats);
        return true;
    }

    public List<Flight> getAllFlights(){
        return flightRepository.findAll();
    }

    /**
     * Finds flights starting at @from flying to @to
     * @param from
     * @param to
     * @return
     */
    public Optional<List<Flight>> findFlightsFromTo(Airport from, Airport to){
        return Optional.of(flightRepository.getFlightsFromTo(from.getAirportId(),to.getAirportId()));
    }
}
