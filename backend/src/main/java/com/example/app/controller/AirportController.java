package com.example.app.controller;

import com.example.app.dto.AirportDto;
import com.example.app.mapper.MapperDto;
import com.example.app.model.Airport;
import com.example.app.service.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1/airport")
@AllArgsConstructor
@CrossOrigin
public class AirportController {
    private final AirportService airportService;
    @PostMapping
    public ResponseEntity<String> addAirport(@RequestBody AirportDto airportDto){
        Optional<Airport> optAirport = airportService.findByName(airportDto.getAirportName());
        if(!optAirport.isEmpty()){
            return new ResponseEntity<>("This airport is already registered.",HttpStatus.BAD_REQUEST);
        }
        airportService.addAirport(MapperDto.createEntity(airportDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AirportDto>> getAllAirports(){
        List<AirportDto> result = new ArrayList<>();
        airportService.getAll().stream().forEach(airp -> result.add(MapperDto.createDto(airp)));
        return new ResponseEntity<List<AirportDto>>(result,HttpStatus.OK);
    }

    /**
     * Deletes all the flight landing of starting at the airport
     * which will be deleted.
     * @param airportDto
     */
    @DeleteMapping
    ResponseEntity<String> deleteAirport(@RequestBody AirportDto airportDto){
        Optional<Airport> optAirport = airportService.findByName(airportDto.getAirportName());
        if(optAirport.isEmpty()){
            return new ResponseEntity<>("This airport was not found..",HttpStatus.BAD_REQUEST);
        }
        airportService.deleteAirport(optAirport.get().getAirportId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
