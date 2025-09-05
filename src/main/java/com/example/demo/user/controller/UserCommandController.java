package com.example.demo.user.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.example.demo.user.service.UserService;
import com.example.demo.auth.dto.request.UpdateUserRequest;
import com.example.demo.user.entity.User;

import java.util.UUID;

/**
 * UserCommandController handles command operations (such as update and delete)
 * related to the User entity.
 *
 * This controller is mapped to the base path "/api/users" and is designed
 * for modifying user-related data.
 *
 * It uses constructor injection to include the UserService.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserCommandController {

    private final UserService userService;

    /**
     * Updates an existing user with the given ID using the provided request data.
     *
     * This endpoint consumes and produces JSON, and applies validation to the request body.
     * It delegates the update operation to the UserService.
     *
     * Parameters:
     * - id: the UUID of the user to be updated, extracted from the URL path
     * - request: the UpdateUserRequest object containing updated user details, validated automatically
     *
     * Returns:
     * A ResponseEntity containing the updated User object and HTTP status 201 (Created)
     *
     * Throws:
     * - ResponseStatusException with status 404 if the user is not found
     * - MethodArgumentNotValidException if the request body fails validation
     */
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(request, id));
    }
}
