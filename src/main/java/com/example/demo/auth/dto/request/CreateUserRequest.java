package com.example.demo.auth.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * CreateUserRequest represents the data required to create a new user.
 *
 * This record is used as a request body in user creation endpoints and includes
 * validation annotations to ensure input correctness.
 *
 * Fields:
 * - name: must not be blank
 * - email: must not be blank and must follow a valid email format
 * - password: must not be blank
 * - phones: optional list of phone numbers, each validated individually
 *
 * Example usage:
 * {
 *   "name": "John Doe",
 *   "email": "john.doe@example.com",
 *   "password": "securePassword123",
 *   "phones": [ { "number": "123456789", "cityCode": "1", "countryCode": "57" } ]
 * }
 *
 * Each phone must conform to validation rules defined in the PhoneDto class.
 */
public record CreateUserRequest(
        @NotBlank(message = "name must not be empty")
        String name,

        @NotBlank(message = "email must not be empty")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "email must be a valid email address")
        String email,

        @NotBlank(message = "password must not be empty")
        String password,

        List< @Valid PhoneDto> phones
) {}
