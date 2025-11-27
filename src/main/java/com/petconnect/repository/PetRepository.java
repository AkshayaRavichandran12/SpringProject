package com.petconnect.repository;

import com.petconnect.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // Get all pets added by a specific user (owner)
    List<Pet> findByOwnerId(Long ownerId);
    List<Pet> findByOwnerIdNot(Long ownerId);

}
