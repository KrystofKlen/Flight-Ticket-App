package com.example.app.unit.mapper;

import com.example.app.dto.BookingDto;
import com.example.app.dto.PersonDto;
import com.example.app.mapper.MapperDto;
import com.example.app.model.Airport;
import com.example.app.model.Booking;
import com.example.app.model.Flight;
import com.example.app.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperDtoTest {

    Airport london = new Airport(1L,"London");
    Airport paris = new Airport(2L,"Paris");
    Flight f1 = new Flight(1L,1,london.getAirportId(),paris.getAirportId());

    Person p1 = new Person(1L,"email");
    PersonDto pdto1 = PersonDto.builder().personId(1L).email("email").build();
    Booking b1 = new Booking(1L,p1,f1);


    @Test
    void canDoBothWayConversion_Flight_FlightDto() {
        Assertions.assertTrue(MapperDto.createEntity(
                MapperDto.createDto(f1,london,paris),london,paris).equals(f1)
        );
    }

    @Test
    void canDoBothWayConversion_Person_PersonDto() {
        assertTrue(
                MapperDto.createEntity(
                        MapperDto.createDto(p1)
                ).equals(p1)
        );
        assertTrue(
                MapperDto.createDto(MapperDto.createEntity(pdto1)).equals(pdto1)
        );
    }

    @Test
    void canDoBothWayConversion_Booking_BookingDto() {
        var dto = MapperDto.createDto(b1,london,paris);
        var entity = MapperDto.createEntity( dto,f1,p1 );
        assertTrue(b1.equals(entity));
        BookingDto bdto = new BookingDto(1L,pdto1, MapperDto.createDto(f1,london,paris));
        entity = MapperDto.createEntity(bdto,f1,p1);
        dto = MapperDto.createDto(entity,london,paris);
        assertTrue(dto.equals(bdto));
    }

}