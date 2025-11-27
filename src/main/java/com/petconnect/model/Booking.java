package com.petconnect.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Pet Details
    @ManyToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnoreProperties({"owner", "bookings"})
    private Pet pet;

    // Booked User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"bookings", "pets", "password"})
    private User user;

    private String petName;
    private String bookingDate;

    private String contact;

    @Column(nullable = false)
    private String status = "PENDING";

    private LocalDateTime bookedAt = LocalDateTime.now();

    // ---------- Getters & Setters ----------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }
}
