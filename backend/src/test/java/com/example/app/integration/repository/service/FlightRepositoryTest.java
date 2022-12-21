package com.example.app.integration.repository.service;

import com.example.app.AppApplication;
import com.example.app.model.Flight;
import com.example.app.repository.FlightRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
class FlightRepositoryTest {

    @Autowired
    private FlightRepository underTest;
    @Test
    @Disabled
    void itShouldChangeNumberOfFreeSeats(){
        final int FREE_SEATS = 30;
        //given
        Flight flight = new Flight(1L,FREE_SEATS,2L,3L);
        underTest.save(flight);
        //when
        for(Integer i = 0; i<FREE_SEATS;i++){
            underTest.increaseFreeSeats(1L,i);

            //then
            Integer freeSeats = underTest.findById(1L).get().getFreeSeats();
            assert (freeSeats.equals(i));
        }
    }

}
