package com.example.app.mapper;

import com.example.app.dto.AirportDto;
import com.example.app.dto.BookingDto;
import com.example.app.dto.FlightDto;
import com.example.app.dto.PersonDto;
import com.example.app.model.*;
import com.example.app.service.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

public class MapperDto {
    public static AirportDto createDto(Airport airport) {
        return AirportDto.builder()
                .airportName(airport.getAirportName())
                .build();
    }
    public static BookingDto createDto(Booking  booking, Airport from, Airport to) {
        return BookingDto.builder()
                .flightDto(createDto(booking.getFlight(),from,to))
                .personDto(createDto(booking.getPerson()))
                .bookingId(booking.getBookingId())
                .build();
    }

    public static PersonDto createDto(Person person) {
        return PersonDto
                .builder()
                .personId(person.getPersonId())
                .email(person.getEmail())
                .build();
    }

    public static FlightDto createDto(Flight flight, Airport from, Airport to) {
        return FlightDto
                .builder()
                .flightId(flight.getFlightId())
                .from(createDto(from))
                .to(createDto(to))
                .freeSeats(flight.getFreeSeats())
                .build();
    }

    public static Person createEntity(PersonDto personDto) {
        Person p = new Person();
        p.setEmail(personDto.getEmail());
        p.setPersonId(personDto.getPersonId());
        return p;
    }

    public static Airport createEntity(AirportDto airportDto) {
        Airport a = new Airport();
        a.setAirportName(airportDto.getAirportName());
        return a;
    }

    public static Flight createEntity(FlightDto flightDto, Airport from, Airport to) {
        Flight f = new Flight();
        f.setFlightId(flightDto.getFlightId());
        f.setFromAirportId(from.getAirportId());
        f.setToAirportId(to.getAirportId());
        f.setFreeSeats(flightDto.getFreeSeats());
        return f;
    }

    public static Booking createEntity(BookingDto bookingDto,Flight f,Person p) {
        Booking booking = new Booking();
        booking.setFlight(f);
        booking.setPerson(p);
        booking.setBookingId( bookingDto.getBookingId() );
        return booking;
    }
}