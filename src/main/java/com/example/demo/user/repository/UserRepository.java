package com.example.demo.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.user.entity.User;

/**
 * UserRepository provides CRUD operations and custom queries for User entities.
 *
 * This interface extends JpaRepository to leverage Spring Data JPA functionalities
 * including pagination, sorting, and basic CRUD methods.
 *
 * Additional method:
 * - findByEmail(String email): retrieves a User by their unique email address.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a User entity by its email address.
     *
     * @param email the email of the user to find
     * @return an Optional containing the User if found, or empty if not
     */
    Optional<User> findByEmail(String email);
}
