package com.petconnect.repository;

import com.petconnect.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {


// Bookings sent by a user
List<Booking> findByUserId(Long userId);

// Bookings received for user's pets
@Query("SELECT b FROM Booking b WHERE b.pet.owner.id = :ownerId")
List<Booking> findByPetOwnerId(@Param("ownerId") Long ownerId);


}
