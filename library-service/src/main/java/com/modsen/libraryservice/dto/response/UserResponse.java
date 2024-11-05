package com.modsen.libraryservice.dto.response;

/**
 * Data Transfer Object (DTO) for representing user information in responses.
 *
 * This class encapsulates the username of a user, typically used in authentication
 * and authorization contexts within the library service.
 *
 * @param username the username of the user
 */
public record UserResponse(String username) {
}
