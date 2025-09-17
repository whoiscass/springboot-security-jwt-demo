package com.example.demo.auth.controller;

import com.example.demo.auth.dto.request.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import com.example.demo.auth.dto.request.CreateUserRequest;
import com.example.demo.auth.dto.response.RegisterUserResponse;
import com.example.demo.user.service.UserService;

import java.util.concurrent.CompletableFuture;

/**
 * Controller for handling authentication-related requests.
 * Provides endpoints for user registration.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Registers a new user based on the provided request data.
     *
     * @param request The request object containing user registration details.
     * @return A ResponseEntity containing the created user's details and HTTP status 201 (Created).
     */
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public CompletableFuture<ResponseEntity<RegisterUserResponse>> create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request)
                .thenApply(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    /**
     * Handles user login requests.
     *
     * Accepts a JSON payload with login credentials,
     * processes the login through the user service,
     * and returns a response containing user registration details.
     *
     * @param request the login request containing user credentials
     * @return a ResponseEntity containing the RegisterUserResponse with HTTP status CREATED
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegisterUserResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(request));
    }

}