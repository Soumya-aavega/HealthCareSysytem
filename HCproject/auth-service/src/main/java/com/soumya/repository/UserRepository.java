package com.soumya.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.soumya.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    // Custom query methods can be defined here if needed
    // For example, you can add a method to find users by username or email
    Optional<User> findByUsername(String username);
    
    Optional<User> findbyExistUsername(String username);

    Optional<User> existByEmail(String email);
}
