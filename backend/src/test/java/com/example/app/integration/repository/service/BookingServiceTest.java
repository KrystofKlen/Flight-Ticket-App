package com.example.app.integration.repository.service;

import com.example.app.AppApplication;
import com.example.app.model.Airport;
import com.example.app.model.Booking;
import com.example.app.model.Flight;
import com.example.app.model.Person;
import com.example.app.repository.AirportRepository;
import com.example.app.repository.BookingRepository;
import com.example.app.repository.FlightRepository;
import com.example.app.repository.PersonRepository;
import com.example.app.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(classes = AppApplication.class)
@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private BookingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BookingService(bookingRepository);
    }


    @Test
    void canGetAllBookingsForPerson() {
        //given
        Airport london = new Airport(1L,"London");
        Airport paris = new Airport(2L,"Paris");
        Airport barcelona = new Airport(3L,"Barcelona");
        Flight f1 = new Flight(1L,1,london.getAirportId(),paris.getAirportId());
        Flight f2 = new Flight(2L,1,london.getAirportId(),paris.getAirportId());
        Flight f3 = new Flight(3L,1,paris.getAirportId(),barcelona.getAirportId());
        Person p1 = new Person(1L,"email");
        Person p2 = new Person(2L,"email");
        Booking b1 = new Booking(1L,p1,f1);
        Booking b2 = new Booking(1L,p2,f2);
        Booking b3 = new Booking(2L,p1,f3);
        Booking b4 = new Booking(1L,p1,f1);
        airportRepository.save(london);
        airportRepository.save(paris);
        airportRepository.save(barcelona);
        flightRepository.save(f1);
        flightRepository.save(f2);
        flightRepository.save(f3);
        personRepository.save(p1);
        personRepository.save(p2);

        var result1 =  underTest.getBookings(1L);
        assertTrue(result1.size() == 3);



    }

}