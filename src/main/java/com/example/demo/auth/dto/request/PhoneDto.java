package com.example.demo.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * PhoneDto represents a phone number associated with a user.
 *
 * This record is used as part of user-related request payloads (e.g., CreateUserRequest)
 * and includes validation to ensure that all fields are provided.
 *
 * Fields:
 * - number: the phone number (must not be blank)
 * - cityCode: the city dialing code (must not be blank)
 * - countryCode: the country dialing code (must not be blank)
 *
 * Example:
 * {
 *   "number": "123456789",
 *   "cityCode": "1",
 *   "countryCode": "57"
 * }
 */
public record PhoneDto (
        @NotBlank(message = "number must not be empty")
        String number,

        @NotBlank(message = "cityCode must not be empty")
        String cityCode,

        @NotBlank(message = "countryCode must not be empty")
        String countryCode
) {}
