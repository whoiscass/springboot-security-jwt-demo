package com.example.demo.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.demo.auth.dto.request.LoginRequest;
import com.example.demo.config.security.JwtService;
import com.example.demo.auth.dto.response.RegisterUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

import com.example.demo.auth.dto.request.CreateUserRequest;
import com.example.demo.user.entity.Phone;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

/**
 * UserService handles business logic related to user management.
 *
 * It provides functionalities for creating users, retrieving user information,
 * and converting request data into entity models.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Creates a new user based on the provided CreateUserRequest.
     *
     * Validates that the email is not already registered,
     * hashes the password, generates a JWT token, and persists the user.
     *
     * @param request the user creation request containing user details
     * @return a RegisterUserResponse representing the created user
     * @throws ResponseStatusException if the email is already registered
     */
    public RegisterUserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phones(getPhonesFromRequest(request))
                .created(now)
                .modified(now)
                .lastLogin(now)
                .build();

        user.setToken(jwtService.generateToken(user));

        user = userRepository.save(user);

        return new RegisterUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getToken(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.isActive()
        );
    }

    public RegisterUserResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email());
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        User user = userOpt.get();

        LocalDateTime now = LocalDateTime.now();

        user.setLastLogin(now);
        user.setToken(jwtService.generateToken(user));

        user = userRepository.save(user);

        return new RegisterUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getToken(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.isActive()
        );
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the UUID of the user to retrieve
     * @return a RegisterUserResponse representing the found user
     * @throws ResponseStatusException if the user is not found
     */
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Converts the list of Phone DTOs from the CreateUserRequest
     * into a list of Phone entity objects.
     *
     * @param request the user creation request containing phone data
     * @return a list of Phone entities
     */
    private List<Phone> getPhonesFromRequest(CreateUserRequest request) {
        return request.phones().stream()
                .map(phoneRequest -> {
                    Phone phone = new Phone();
                    phone.setNumber(phoneRequest.number());
                    phone.setCityCode(phoneRequest.cityCode());
                    phone.setCountryCode(phoneRequest.countryCode());
                    return phone;
                })
                .collect(Collectors.toList());
    }
}
