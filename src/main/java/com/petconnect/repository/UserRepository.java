package com.petconnect.repository;

import com.petconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // find user using email
    User findByEmail(String email);

    // login check
    User findByEmailAndPassword(String email, String password);
}
