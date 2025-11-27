package com.petconnect.service;

import org.springframework.stereotype.Service;

import com.petconnect.model.Booking;
import com.petconnect.repository.BookingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;

    public BookingService(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    // Save booking
    public Booking book(Booking booking) {
        return bookingRepo.save(booking);
    }

    // Fetch bookings of a specific user
    public List<Booking> forUser(Long userId) {
        return bookingRepo.findByUserId(userId);
    }

    // Accept a booking
    public Booking acceptBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepo.findById(bookingId);
        if (bookingOpt.isEmpty())
            throw new RuntimeException("Booking not found");

        Booking booking = bookingOpt.get();
        booking.setStatus("ACCEPTED");
        return bookingRepo.save(booking);
    }
}
