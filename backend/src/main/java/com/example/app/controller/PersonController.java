package com.example.app.controller;

import com.example.app.dto.BookingDto;
import com.example.app.dto.FlightDto;
import com.example.app.dto.PersonDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/person")
@AllArgsConstructor
@CrossOrigin
public class PersonController {
    private final PersonService personService;
    private final BookingController bookingController;
    private final BookingService bookingService;
    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody PersonDto personDto){
        Optional<Person> optP = personService.findByEmail(personDto.getEmail());
        if( ! optP.isEmpty()){
            return new ResponseEntity<>("This email is already in use.",HttpStatus.BAD_REQUEST);
        }
        personService.addPerson(MapperDto.createEntity(personDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAll(){
        List<PersonDto> result = personService.getAll().stream().map(MapperDto::createDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /**
     * Deletes person and ALL bookings which person has.
     * @param personId
     * @return
     */
    @DeleteMapping("/del/{personId}")
    public ResponseEntity<String> deletePerson(@PathVariable Long personId){
        Optional<Person> optPerson = personService.getPerson(personId);
        if(optPerson.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long id = optPerson.get().getPersonId();
        List<Booking> bookings = bookingService.getBookings(id);
        // cancel all bookings
        for(var booking : bookings){
            Optional<Airport> from = airportService.findById(booking.getFlight().getFromAirportId());
            Optional<Airport> to = airportService.findById(booking.getFlight().getToAirportId());
            bookingController.cancelBooking(MapperDto.createDto(booking,from.get(),to.get()));
        }
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates person data (without ID)
     * @param personDto
     * @return
     */
    @PatchMapping("/patch")
    public ResponseEntity<String> updatePerson(@RequestBody PersonDto personDto){
        Optional<Person> optPerson = personService.getPerson(personDto.getPersonId());
        if(optPerson.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Person> optPersonWithNewEmail = personService.findByEmail(personDto.getEmail());
        if(!optPersonWithNewEmail.isEmpty()) {
            // this email is already using someone else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // update email
        personService.updatePersonEmail(optPerson.get().getPersonId(), personDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/book/{personId}")
    public ResponseEntity<List<BookingDto>> getBookedFlights(@PathVariable Long personId){
        Optional<Person> optPerson = personService.getPerson(personId);
        if(optPerson.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long id = optPerson.get().getPersonId();

        List<BookingDto> result = new ArrayList<>();
        bookingService.getBookings(optPerson.get().getPersonId())
                .stream()
                .forEach(booking -> {
                    Airport airpFrom =  airportService.findById(booking.getFlight().getFromAirportId()).get();
                    Airport airpTo =  airportService.findById(booking.getFlight().getToAirportId()).get();
                    result.add(MapperDto.createDto(booking,airpFrom,airpTo));
                });
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
