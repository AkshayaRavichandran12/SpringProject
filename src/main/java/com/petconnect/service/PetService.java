package com.petconnect.service;

import com.petconnect.model.Pet;
import com.petconnect.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    // Add new pet
    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    // Get all pets
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // Get pets by owner
    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
    
    //get others pet
    public List<Pet> getOtherUsersPets(Long userId) {
        return petRepository.findByOwnerIdNot(userId);
    }


    // Get pet by ID
    public Pet getById(Long id) {
        return petRepository.findById(id).orElse(null);
    }
}
