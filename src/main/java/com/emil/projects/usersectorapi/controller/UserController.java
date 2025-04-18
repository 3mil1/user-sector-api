package com.emil.projects.usersectorapi.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.emil.projects.usersectorapi.dto.UserDto;
import com.emil.projects.usersectorapi.security.service.AuthenticationService;
import com.emil.projects.usersectorapi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<Void> saveUserAndAuthenticate(@RequestBody @Valid UserDto userDto,
            HttpServletRequest request, HttpServletResponse response) {

        UUID userId = userService.saveUser(userDto);
        log.info("User saved with ID: {}", userId);

        authenticationService.authenticateUser(userId, request, response);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/current")
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        log.debug("Received request to get current user");
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Attempted to get current user without valid authentication");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UUID currentUserId = UUID.fromString(userDetails.getUsername());
        log.info("Fetching current user data for authenticated user ID: {}", currentUserId);

        return userService.findById(currentUserId)
                .map(userDto -> {
                    log.debug("Current user found");
                    return ResponseEntity.ok(userDto);
                })
                .orElseGet(() -> {
                    log.error("Authenticated user with ID {} not found in database!", currentUserId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, Authentication authentication) {
        log.debug("Received request to update user");

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Attempted to update user without valid authentication");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UUID currentUserId = UUID.fromString(userDetails.getUsername());
        log.info("Attempting update for authenticated user ID: {}", currentUserId);

        UserDto updatedUserDto = userService.updateUser(currentUserId, userDto);
        log.info("Successfully updated user with ID: {}", currentUserId);
        return ResponseEntity.ok(updatedUserDto);
    }
}