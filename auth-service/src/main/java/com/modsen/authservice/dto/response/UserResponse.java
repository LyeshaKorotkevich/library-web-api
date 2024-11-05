package com.modsen.authservice.dto.response;

/**
 * Data Transfer Object (DTO) for representing user responses.
 * This record is used to convey user information back to the client.
 *
 * @param username the username of the user
 */
public record UserResponse(String username) {
}
