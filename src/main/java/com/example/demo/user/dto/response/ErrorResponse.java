package com.example.demo.user.dto.response;

/**
 * ErrorResponse represents a standardized structure for sending error messages
 * from the API to the client.
 *
 * This record is typically used in exception handling to return a clear error message
 * when a request fails due to validation errors, unauthorized access, or other issues.
 *
 * Field:
 * - message: a human-readable description of the error
 *
 * Example:
 * {
 *   "message": "Email must be a valid email address"
 * }
 */
public record ErrorResponse(
        String message
) {}
