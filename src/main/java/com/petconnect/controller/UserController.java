package com.petconnect.controller;

import com.petconnect.model.User;
import com.petconnect.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ➤ GET USER BY ID (Profile Page)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(user.get());
    }

    // ➤ UPDATE USER PROFILE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newData) {

        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = opt.get();

        user.setFullname(newData.getFullname());
        user.setEmail(newData.getEmail());
        user.setPhone(newData.getPhone());
        user.setAddress(newData.getAddress());

        User updated = userRepository.save(user);
        return ResponseEntity.ok(updated);
    }

    // ➤ GET ALL USERS (not needed but useful for admin)
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
