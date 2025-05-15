package com.soumya.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.soumya.model.Role;

public interface  RoleRepository extends MongoRepository<Role, String> {
    // Custom query methods can be defined here if needed
    // For example, you can add a method to find roles by name
     Optional<Role> findByName(String name);

}
