package com.example.demo.auth.dto.request;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserResponse represents a data transfer object (DTO) for sending user information to the client.
 *
 * This record mirrors the structure of the User entity without exposing sensitive internal behavior or annotations.
 *
 * Fields:
 * - name: Full name of the user
 * - email: Email address of the user
 * - password: Encrypted password (optional to expose; usually not included in responses)
 * - phones: List of phones associated with the user
 * - modified: Timestamp of the last modification
 * - isActive: Boolean flag indicating if the user is active
 */
public record UpdateUserRequest(
        String name,
        String email,
        String password,
        List<PhoneDto> phones,
        LocalDateTime modified,
        boolean isActive
) {}
