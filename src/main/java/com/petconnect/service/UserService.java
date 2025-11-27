package com.petconnect.service;

import org.springframework.stereotype.Service;

import com.petconnect.model.User;
import com.petconnect.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Register new user
    public User register(User user) {
        return userRepo.save(user);
    }

    // Login user
    public Optional<User> login(String email, String password) {
        User u = userRepo.findByEmail(email);
        if (u != null && u.getPassword().equals(password)) {
            return Optional.of(u);
        }
        return Optional.empty();
    }

    // Get user by ID
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }
}
