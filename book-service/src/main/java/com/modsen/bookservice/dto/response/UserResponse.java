package com.modsen.bookservice.dto.response;

/**
 * Represents the response data for a user.
 *
 * This record is used for transferring user information from the server
 * to the client, specifically for authentication and authorization purposes.
 *
 * @param username the username of the user
 */
public record UserResponse(String username) {
}
