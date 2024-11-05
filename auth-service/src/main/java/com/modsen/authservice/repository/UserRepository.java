package com.modsen.authservice.repository;

import com.modsen.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing {@link User} entities in the database.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return an {@link Optional} containing the found {@link User}, or empty if no user found
     */
    Optional<User> findByUsername(String username);
}
