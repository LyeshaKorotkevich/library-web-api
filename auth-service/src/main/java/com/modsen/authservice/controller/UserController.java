package com.modsen.authservice.controller;

import com.modsen.authservice.dto.response.UserResponse;
import com.modsen.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for managing user-related operations.
 * Provides endpoints to retrieve user information.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves user information by username.
     *
     * @param username the username of the user to retrieve
     * @return a ResponseEntity containing the UserResponse DTO with user information
     */
    @Operation(summary = "Get user by username",
            description = "Retrieves user information based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(
            @Parameter(description = "The username of the user to retrieve")
            @PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }
}
