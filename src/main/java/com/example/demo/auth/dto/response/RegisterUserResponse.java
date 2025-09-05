package com.example.demo.auth.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * RegisterUserResponse represents the data returned from the API when querying user details.
 *
 * This record encapsulates user information including identification, contact,
 * authentication token, timestamps, and status.
 *
 * Fields:
 * - id: the unique identifier of the user
 * - name: the user's full name
 * - email: the user's email address
 * - token: the JWT token associated with the user session
 * - created: the timestamp when the user was created
 * - modified: the timestamp when the user was last modified
 * - lastLogin: the timestamp of the user's last login
 * - isActive: indicates whether the user account is active
 *
 * Example:
 * {
 *   "id": "123e4567-e89b-12d3-a456-426614174000",
 *   "name": "Jane Doe",
 *   "email": "jane.doe@example.com",
 *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
 *   "created": "2024-01-01T12:00:00",
 *   "modified": "2024-01-10T15:30:00",
 *   "lastLogin": "2024-02-01T09:45:00",
 *   "isActive": true
 * }
 */
public record RegisterUserResponse(
        UUID id,
        String name,
        String email,
        String token,
        LocalDateTime created,
        LocalDateTime modified,
        LocalDateTime lastLogin,
        Boolean isActive
) {}
