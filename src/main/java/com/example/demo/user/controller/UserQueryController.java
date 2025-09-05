package com.example.demo.user.controller;

import com.example.demo.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.demo.user.service.UserService;

import java.util.UUID;

/**
 * UserQueryController handles query (read-only) operations related to the User entity.
 *
 * This controller is mapped to the base path "/api/users" and provides endpoints
 * for retrieving user information.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserQueryController {

    private final UserService userService;

    /**
     * Retrieves a user by their unique identifier.
     *
     * This endpoint responds with a RegisterUserResponse containing user data if found.
     *
     * @param id the UUID of the user to retrieve
     * @return ResponseEntity containing the RegisterUserResponse and HTTP status 200 OK
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }
}
