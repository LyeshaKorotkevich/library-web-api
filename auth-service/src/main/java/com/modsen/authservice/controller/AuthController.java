package com.modsen.authservice.controller;

import com.modsen.authservice.dto.request.LoginRequest;
import com.modsen.authservice.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication-related operations.
 * Provides endpoints for user authentication.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginRequest the login credentials containing username and password
     * @return a ResponseEntity containing the generated JWT token
     */
    @Operation(summary = "Authenticate user",
            description = "Validates user credentials and generates a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, token returned"),
            @ApiResponse(responseCode = "401", description = "Authentication failed, invalid credentials")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @Parameter(description = "Login request containing username and password")
            @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenUtil.generateToken(authentication);

        return ResponseEntity.ok(token);
    }
}
