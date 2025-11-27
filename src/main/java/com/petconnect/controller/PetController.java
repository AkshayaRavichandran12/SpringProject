package com.petconnect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petconnect.model.Pet;
import com.petconnect.model.User;
import com.petconnect.repository.PetRepository;
import com.petconnect.repository.UserRepository;
import com.petconnect.service.FileStorageService;
import com.petconnect.service.PetService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "*")
public class PetController {

    private final PetService petService;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public PetController(PetService petService,
                         FileStorageService fileStorageService,
                         UserRepository userRepository,
                         ObjectMapper objectMapper) {
        this.petService = petService;
        this.fileStorageService = fileStorageService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    // --------------------------- ADD PET -----------------------------
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addPet(
            @RequestPart("pet") String petJson,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "ownerId", required = false) Long ownerId) {

        try {
            Pet pet = objectMapper.readValue(petJson, Pet.class);

            if (pet.getName() == null || pet.getName().isBlank()) {
                return ResponseEntity.badRequest().body("Pet name is required");
            }

            // Set Owner
            if (ownerId != null) {
                Optional<User> ownerOpt = userRepository.findById(ownerId);
                if (ownerOpt.isEmpty()) {
                    return ResponseEntity.badRequest().body("Invalid ownerId");
                }
                pet.setOwner(ownerOpt.get());
            }

            // Upload image
            if (image != null && !image.isEmpty()) {
                String filename = fileStorageService.storeFile(image);
                pet.setImagePath(filename);
            }

            Pet saved = petService.addPet(pet);
            return ResponseEntity.ok(saved);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid pet JSON: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to add pet");
        }
    }

    // --------------------------- GET ALL PETS -------------------------
    @GetMapping
    public ResponseEntity<?> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }
    
    
 // --------------------------- GET PET BY ID ------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Long id) {
        Pet pet = petService.getById(id);
        if (pet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }
        return ResponseEntity.ok(pet);
    }


    // --------------------------- PETS BY OWNER ------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> petsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(petService.getPetsByOwner(userId));
    }
    @GetMapping("/others/{userId}")
    public ResponseEntity<?> getOtherPets(@PathVariable Long userId) {
        return ResponseEntity.ok(petService.getOtherUsersPets(userId));
    }

}
