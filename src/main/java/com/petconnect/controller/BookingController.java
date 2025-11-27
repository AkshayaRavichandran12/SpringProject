package com.petconnect.controller;

import com.petconnect.model.Booking;
import com.petconnect.model.Pet;
import com.petconnect.model.User;
import com.petconnect.repository.BookingRepository;
import com.petconnect.repository.PetRepository;
import com.petconnect.repository.UserRepository;
import com.petconnect.service.BookingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final BookingService bookingService;

    public BookingController(BookingRepository bookingRepository,
                             PetRepository petRepository,
                             UserRepository userRepository,
                             BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.bookingService = bookingService;
    }

    // ----------------------- CREATE BOOKING -----------------------
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            if (booking.getPet() == null || booking.getPet().getId() == null)
                return ResponseEntity.badRequest().body("Pet ID missing");

            if (booking.getUser() == null || booking.getUser().getId() == null)
                return ResponseEntity.badRequest().body("User ID missing");

            Optional<Pet> petOpt = petRepository.findById(booking.getPet().getId());
            Optional<User> userOpt = userRepository.findById(booking.getUser().getId());

            if (petOpt.isEmpty() || userOpt.isEmpty())
                return ResponseEntity.badRequest().body("Invalid pet/user ID");

            Pet pet = petOpt.get();
            User user = userOpt.get();

            booking.setPet(pet);
            booking.setUser(user);

            if (booking.getPetName() == null)
                booking.setPetName(pet.getName());

            if (booking.getBookingDate() == null)
                booking.setBookingDate(LocalDateTime.now().toLocalDate().toString());

            booking.setStatus("PENDING");

            Booking saved = bookingRepository.save(booking);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Booking failed: " + e.getMessage());
        }
    }

    // ----------------------- GET BOOKINGS BY USER -----------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookings(@PathVariable Long userId) {
        try {
            List<Booking> sentBookings = bookingRepository.findByUserId(userId);
            List<Booking> receivedBookings = bookingRepository.findByPetOwnerId(userId);

            return ResponseEntity.ok(Map.of(
                    "sentBookings", sentBookings,
                    "receivedBookings", receivedBookings
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error loading bookings");
        }
    }

    // ----------------------- ACCEPT BOOKING -----------------------
    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptBooking(@PathVariable Long id) {
        try {
            Optional<Booking> opt = bookingRepository.findById(id);
            if (opt.isEmpty())
                return ResponseEntity.status(404).body("Booking not found");

            Booking booking = opt.get();

            booking.setStatus("ACCEPTED");
            bookingRepository.save(booking);

            return ResponseEntity.ok(Map.of(
                    "message", "Booking Accepted",
                    "booking", booking
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Accept failed");
        }
    }

    // ----------------------- CANCEL BOOKING -----------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        try {
            Optional<Booking> opt = bookingRepository.findById(id);

            if (opt.isEmpty())
                return ResponseEntity.badRequest().body("Booking not found");

            Booking booking = opt.get();

            if ("ACCEPTED".equals(booking.getStatus()))
                return ResponseEntity.badRequest().body("Cannot cancel accepted booking");

            bookingRepository.deleteById(id);
            return ResponseEntity.ok("Booking cancelled");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Cancel failed");
        }
    }
}
