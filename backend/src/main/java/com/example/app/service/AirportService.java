package com.example.app.service;

import com.example.app.model.Airport;
import com.example.app.repository.AirportRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;
    private final FlightService flightService;
    private final EntityManagerFactory entityManagerFactory;

    public void addAirport(Airport airp){
        airportRepository.save(airp);
    }

    /**
     * Deletes all the flight landing of starting at the airport
     * which will be deleted.
     * @param airpId
     */
    public void deleteAirport(Long airpId){
        flightService.getAllFlights().stream().forEach(
                flight -> {
                    flightService.deleteFlight(flight.getFlightId());
                }
        );
        airportRepository.deleteById(airpId);
    }

    public List<Airport> getAll(){
        return airportRepository.findAll();
    }

    public Optional<Airport> findById(Long id){
        return airportRepository.findById(id);
    }
    public Optional<Airport> findByName(String airportName){

        List<Airport> airp = airportRepository.findByName(airportName);
        if(airp.isEmpty()){
            return Optional.of(null);
        }
        return Optional.of(airp.get(0));
//
//        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
//        CriteriaQuery<Airport> cq = cb.createQuery(Airport.class);
//
//        Root<Airport> airp = cq.from(Airport.class);
//        Predicate pred= cb.equal(airp.get("airportName"),airportName);
//        cq.where(pred);
//        Optional<TypedQuery<Airport> > optQuery
//                = Optional.of(entityManagerFactory.createEntityManager().createQuery(cq));
//        if(optQuery.isEmpty() || optQuery.get().getResultList().isEmpty()){
//            return Optional.empty();
//        }
//        return Optional.of(optQuery.get().getResultList().stream().findFirst().get());
    }
}
