package com.example.demo.user.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.demo.user.service.UserService;

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

    // Command operations (Update, Delete) for User business entity
}
