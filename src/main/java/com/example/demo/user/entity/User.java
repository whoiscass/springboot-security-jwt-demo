package com.example.demo.user.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * User represents a user entity in the system.
 *
 * This class is mapped to the "users" table in the database.
 * It contains user-specific information such as identity, credentials,
 * associated phone numbers, timestamps, authentication token, and active status.
 *
 * Fields:
 * - id: the unique identifier of the user, generated as a UUID
 * - name: the user's full name
 * - email: the user's email address, unique in the database
 * - password: the user's password (typically hashed)
 * - phones: list of phones associated with the user
 * - created: timestamp when the user record was created, set automatically
 * - modified: timestamp when the user record was last updated, set automatically
 * - lastLogin: timestamp of the user's last login
 * - token: JWT token associated with the user session
 * - isActive: indicates if the user account is active (defaults to true)
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * Unique identifier for the user.
     * Generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Full name of the user.
     */
    private String name;

    /**
     * Unique email address of the user.
     */
    @Column(unique = true)
    private String email;

    /**
     * User's password (should be stored securely).
     */
    private String password;

    /**
     * List of phone numbers associated with the user.
     * Cascade type ALL and lazy fetching are applied.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Phone> phones;

    /**
     * Timestamp when the user was created.
     * Automatically set on creation.
     */
    @CreationTimestamp
    private LocalDateTime created;

    /**
     * Timestamp when the user was last modified.
     * Automatically updated on modification.
     */
    @UpdateTimestamp
    private LocalDateTime modified;

    /**
     * Timestamp of the user's last login.
     */
    private LocalDateTime lastLogin;

    /**
     * JWT token associated with the user session.
     */
    private String token;

    /**
     * Indicates whether the user account is active.
     * Defaults to true.
     */
    @Builder.Default
    private boolean isActive = true;

    /**
     * Custom string representation of the User entity.
     * Includes id, name, email, timestamps, and active status.
     *
     * @return a string describing the user object
     */
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "', created=" + created + ", modified=" + modified + ", lastLogin=" + lastLogin + ", isActive=" + isActive + "}";
    }
}
