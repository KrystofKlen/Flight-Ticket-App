package com.example.app.service;

import com.example.app.model.Booking;
import com.example.app.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public void save(Booking b){
        bookingRepository.save(b);
    }
    public Optional<Booking> findById(Long id){
        return bookingRepository.findById(id);
    }
    public void deleteById(Long id){
        bookingRepository.deleteById(id);
    }

    public List<Booking> getBookings(Long personId){
        return
        bookingRepository.findAll().stream()
                .filter(booking -> booking.getPerson().getPersonId().equals(personId))
                .collect(Collectors.toList());
    }

    public List<Booking> getBookingsForFlight(Long flightID){
        return  bookingRepository.getBookingsForFlight(flightID);
    }
}
