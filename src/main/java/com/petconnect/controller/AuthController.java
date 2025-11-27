package com.petconnect.controller;

import com.petconnect.model.User;
import com.petconnect.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ---------------------- REGISTER ----------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        // Check if email already exists
        User existing = userRepository.findByEmail(user.getEmail());
        if (existing != null) {
            return ResponseEntity.status(409).body("Email already registered");
        }

        // Save new user
        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    // ---------------------- LOGIN --------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {

        User dbUser = userRepository.findByEmail(loginRequest.getEmail());

        if (dbUser == null) {
            return ResponseEntity.status(401).body("Invalid email");
        }

        if (!dbUser.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // Return full user
        return ResponseEntity.ok(dbUser);
    }
}
