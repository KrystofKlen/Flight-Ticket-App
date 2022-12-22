package com.example.app.controller;

import com.example.app.dto.BookingDto;
import com.example.app.dto.FlightDto;
import com.example.app.mapper.MapperDto;
import com.example.app.model.Airport;
import com.example.app.model.Booking;
import com.example.app.model.Flight;
import com.example.app.model.Person;
import com.example.app.repository.BookingRepository;
import com.example.app.service.AirportService;
import com.example.app.service.BookingService;
import com.example.app.service.FlightService;
import com.example.app.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/booking")
@AllArgsConstructor
@CrossOrigin
public class BookingController {

    private final FlightService flightService;
    private final PersonService personService;
    private final BookingService bookingService;

    private final AirportService airportService;

    /**
     * person id noot necessary in booking dto, however, person email is requeired to
     * succesfully save a new booking
     * @param bookingDto
     * @return
     */
    @PostMapping
    @Transactional
    public ResponseEntity<String> bookFlight(@RequestBody BookingDto bookingDto){

        Optional<Person> optPers = personService.findByEmail(bookingDto.getPersonDto().getEmail());
        if(optPers.isEmpty()){
            return new ResponseEntity<>("Person not found",HttpStatus.NOT_FOUND);
        }
        Optional<Flight> optFlight = flightService.getFlight(bookingDto.getFlightDto().getFlightId());
        if(optFlight.isEmpty()){
            return new ResponseEntity<>("Flight not found",HttpStatus.NOT_FOUND);
        }
        boolean success
                = flightService.updateFreeSeats(bookingDto.getFlightDto().getFlightId(), -1);
        if(!success){
            return new ResponseEntity<>("Flight already booked out.",HttpStatus.BAD_REQUEST);
        }

        bookingService.save(MapperDto.createEntity(bookingDto,optFlight.get(),optPers.get()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping
    @Transactional
    public ResponseEntity<String> cancelBooking(@RequestBody BookingDto  bookingDto){
        if(bookingDto.getBookingId() == null){
            return new ResponseEntity<>("No bookingId provided",HttpStatus.BAD_REQUEST);
        }
        Optional<Booking> optionalBooking = bookingService.findById(bookingDto.getBookingId());
        if(optionalBooking.isEmpty()){
            return new ResponseEntity<>("Booking not found.",HttpStatus.NOT_FOUND);
        }
        Optional<Flight> f = flightService.getFlight(optionalBooking.get().getFlight().getFlightId());
        bookingService.deleteById(bookingDto.getBookingId());
        flightService.updateFreeSeats(f.get().getFlightId(),1);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BookingDto> getBooking(@RequestBody BookingDto bookingDto){
        if(bookingDto.getBookingId() == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Booking> optionalBooking= bookingService.findById(bookingDto.getBookingId());
        if(optionalBooking.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Airport airpFrom = airportService.findById(optionalBooking.get().getFlight().getFromAirportId()).get();
        Airport airpTo = airportService.findById(optionalBooking.get().getFlight().getToAirportId()).get();
        return new ResponseEntity<>(MapperDto.createDto(optionalBooking.get(),airpFrom,airpTo),HttpStatus.OK);
    }

    /**
     * Increments number of available seats on place by 1.
     * //TODO set final capacity for flights so that there can not be infinite number of free seats
     * @param flightDto
     * @return
     */
    @PatchMapping("/inc")
    public ResponseEntity<String> incrementFreeSeats(@RequestBody FlightDto flightDto){
        if(flightService.getFlight(flightDto.getFlightId()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        flightService.updateFreeSeats(flightDto.getFlightId(),1);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Increments number of available seats on place by 1.
     * @param flightDto
     * @return
     */
    @PatchMapping("/dec")
    public ResponseEntity<String> dectementFreeSeats(@RequestBody FlightDto flightDto){
        if(flightDto.getFlightId() == null){
            return new ResponseEntity<>("No flightId provided.",HttpStatus.BAD_REQUEST);
        }
        Optional<Flight> f = flightService.getFlight(flightDto.getFlightId());
        if(f.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(f.get().getFreeSeats() == 0){
            return new ResponseEntity<>("Cannot decrement capacity, the flight is already booked out.",HttpStatus.BAD_REQUEST);
        }
        flightService.updateFreeSeats(flightDto.getFlightId(),-1);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
