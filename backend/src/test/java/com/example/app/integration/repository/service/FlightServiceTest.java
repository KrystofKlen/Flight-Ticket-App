package com.example.app.integration.repository.service;

import com.example.app.AppApplication;
import com.example.app.model.Airport;
import com.example.app.model.Flight;
import com.example.app.repository.FlightRepository;
import com.example.app.repository.PersonRepository;
import com.example.app.service.FlightService;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(classes = AppApplication.class)
@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private Flight flight;
    //@Autowired
    //private EntityManagerFactory entityManagerFactory;
    private FlightService underTest;

//    @BeforeEach
//    void setUp() {
//        underTest = new FlightService(flightRepository,personRepository,entityManagerFactory);
//    }

    @Autowired
    /*@PersistenceUnit(unitName = "mUnit")
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }*/
    @Test
    @Disabled
    void deleteFlight() {
        // when
        Long id = flight.getFlightId();
        underTest.addFlight(flight);
        underTest.deleteFlight(id);
        //then
        assertTrue(underTest.getFlight(id).isEmpty());
    }

    @Test
    @Disabled
    void getAllFlights() {
    }

    @Test
    @Disabled
    void findFlightsFromTo() {
        // given
        Airport london = new Airport(1L,"London");
        Airport paris = new Airport(2L,"Paris");
        Airport barcelona = new Airport(3L,"Barcelona");
        Airport milan = new Airport(4L,"Milan");
        Airport prague = new Airport(5L,"Prague");

        Flight f1 = new Flight(1L,1,london.getAirportId(),paris.getAirportId());
        Flight f2 = new Flight(2L,1,london.getAirportId(),paris.getAirportId());
        Flight f3 = new Flight(3L,1,paris.getAirportId(),barcelona.getAirportId());
        Flight f4 = new Flight(4L,1,paris.getAirportId(),prague.getAirportId());
        Flight f5 = new Flight(5L,1,prague.getAirportId(),london.getAirportId());
        flightRepository.save(f1);
        flightRepository.save(f2);
        flightRepository.save(f3);
        flightRepository.save(f4);
        flightRepository.save(f5);

        //then
        assertTrue(underTest.findFlightsFromTo(london,prague).isEmpty());
        var result1 = underTest.findFlightsFromTo(paris,prague);
        assertTrue(result1.get().size() == 1);
        assertTrue(result1.get().contains(f4));
        var result2 = underTest.findFlightsFromTo(paris,london);
        assertTrue(result2.isEmpty());
        var result3 = underTest.findFlightsFromTo(milan,paris);
        assertTrue(result3.isEmpty());
        var result4 = underTest.findFlightsFromTo(london,paris);
        assertTrue(result4.get().size() == 2
                && result4.get().containsAll(List.of(f1,f2)) );
    }
}