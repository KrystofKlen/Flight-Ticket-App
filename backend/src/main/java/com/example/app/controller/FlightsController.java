package com.example.app.controller;

import com.example.app.dto.AirportDto;
import com.example.app.dto.FlightDto;
import com.example.app.dto.PersonDto;
import com.example.app.dto.RouteDto;
import com.example.app.mapper.MapperDto;
import com.example.app.model.Airport;
import com.example.app.model.Booking;
import com.example.app.model.Flight;
import com.example.app.repository.BookingRepository;
import com.example.app.service.AirportService;
import com.example.app.service.BookingService;
import com.example.app.service.FlightService;
import jakarta.persistence.PostRemove;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/flight")
@AllArgsConstructor
@CrossOrigin
public class FlightsController {
    private final FlightService flightService;
    private final AirportService airportService;
    private final BookingRepository bookingRepository;

    @PostMapping
    public ResponseEntity<String> addFlight(@RequestBody FlightDto flightDto) {
        Optional<Airport> from = airportService.findByName(flightDto.getFrom().getAirportName());
        Optional<Airport> to = airportService.findByName(flightDto.getTo().getAirportName());
        if (from.isEmpty() || to.isEmpty()) {
            return new ResponseEntity<>("Airport not in the database.", HttpStatus.NOT_FOUND);
        }
        flightService.addFlight(MapperDto.createEntity(flightDto, from.get(), to.get()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes flight if it has no bookings.
     * @param flightId
     * @return
     */
    @DeleteMapping("/del/{flightId}")
    public ResponseEntity<String > deleteFlight(@PathVariable Long flightId){
        // check if flight has bookings
         List<Booking> bookings = bookingRepository.getBookingsForFlight(flightId);
         if( ! bookings.isEmpty()){
             return new ResponseEntity<>(HttpStatus.FORBIDDEN);
         }
        // no booking for the given flight -> delete
        flightService.deleteFlight(flightId);
         return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * @param routeDto
     * @return Returns routes with flights (How to get from `from` to `to`).
     * May contain direct, connecting or no flights if there is no possible connection.
     */
    public ResponseEntity<String> getPossibleFlightsOnRoute(@RequestBody RouteDto routeDto) {
        Optional<Airport> from = airportService.findByName(routeDto.getFrom());
        Optional<Airport> to = airportService.findByName(routeDto.getTo());
        if (from.isEmpty() || to.isEmpty()) {
            return new ResponseEntity<>("Airport not in the database.", HttpStatus.NOT_FOUND);
        }
        //TODO start bfs
        return null;
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        List<FlightDto> result = new ArrayList<>();
        flightService.getAllFlights().stream()
                        .forEach(flight -> {
                            Airport from = airportService.findById(flight.getFromAirportId()).get();
                            Airport to = airportService.findById(flight.getToAirportId()).get();
                            result.add(MapperDto.createDto(flight,from,to));
                        });
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /**
     *
     * @param fromS unique airport name
     * @param toS unique airport name
     * @return
     */
    @GetMapping("/{fromS}/{toS}")
    public ResponseEntity<List<FlightDto>> getFlightsFromTo(
            @PathVariable String fromS,
            @PathVariable String toS
            ){

        fromS = fromS.replaceAll("%20"," ");
        toS = toS.replaceAll("%20"," ");

        Optional<Airport> optionalAirportF =  airportService.findByName(fromS);
        Optional<Airport> optionalAirportT = airportService.findByName(toS);
        if(optionalAirportT.isEmpty() || optionalAirportT.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Airport from = optionalAirportF.get();
        Airport to = optionalAirportT.get();

        Optional<List<Flight>> resultOpt = flightService.findFlightsFromTo(from,to);
        if( resultOpt.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FlightDto> result = new ArrayList<>();
        resultOpt.get().stream()
                .forEach( flight -> {
                    Airport airportFrom= airportService.findById(flight.getFromAirportId()).get();
                    Airport airportTo = airportService.findById(flight.getToAirportId()).get();
                    result.add(MapperDto.createDto(flight,airportFrom,airportTo));
                } );
        System.out.println(result);
        return new ResponseEntity<>(result,HttpStatus.OK);

    }
}
