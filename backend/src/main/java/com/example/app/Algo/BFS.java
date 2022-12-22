package com.example.app.Algo;

import com.example.app.mapper.MapperBFS;
import com.example.app.model.Airport;
import com.example.app.model.Flight;
import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class BFS {



    public static boolean IS_INITIALIZED = false;
    List<FlightBFS> flights;
    List<AirportBFS> airports;


    public List<Long> find(Airport from, Airport to, List<Flight> flights){
        Map<Long,Long> airpPrev = new HashMap<>();
        Queue<Long> queue = new LinkedList<>();
        queue.add(from.getAirportId());
        Set<Long> closed = new HashSet<>();
        Set<Long> open = new HashSet<>();
        open.add(from.getAirportId());
        Long END = -1L;
        airpPrev.put(from.getAirportId(),END);

        while(!queue.isEmpty()){
            Long curr = queue.peek();
            if(curr.equals(to.getAirportId())){
                break;
            }
            List<Flight> flightsFrom = flights.stream().filter(flight ->
                flight.getFromAirportId().equals(curr))
                    .collect(Collectors.toList());
            for(Flight flight : flightsFrom){
                Long toAirportId = flight.getToAirportId();
                if(open.contains(toAirportId) || closed.contains(toAirportId)){

                    continue;
                }
                airpPrev.put(toAirportId,curr);
                queue.add(toAirportId);
                open.add(toAirportId);
            }
            closed.add(curr);
            open.remove(curr);
            queue.poll();
        }

        LinkedList<Long> route = new LinkedList<>();
        Long curr = to.getAirportId();
        while (!curr.equals(END)){
            route.addFirst(curr);
            curr = airpPrev.get(curr);
        }
        return route;
    }



}
