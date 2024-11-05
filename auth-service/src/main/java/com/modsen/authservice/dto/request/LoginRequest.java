package com.modsen.authservice.dto.request;

/**
 * Data Transfer Object (DTO) for user login requests.
 * Contains the necessary credentials for authentication.
 *
 * @param username the username of the user attempting to log in
 * @param password the password of the user attempting to log in
 */
public record LoginRequest(String username, String password) {
}
