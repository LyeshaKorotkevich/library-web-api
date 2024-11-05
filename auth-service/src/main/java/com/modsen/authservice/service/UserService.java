package com.modsen.authservice.service;

import com.modsen.authservice.dto.response.UserResponse;
import com.modsen.authservice.mapper.UserMapper;
import com.modsen.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service for managing user-related operations.
 * Provides methods to retrieve user information and map it to response DTOs.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    /**
     * Finds a user by their username and returns the user data as a {@link UserResponse}.
     *
     * <p>This method fetches a user from the repository based on the provided username,
     * and maps the user entity to a response DTO using {@link UserMapper}.
     * If no user is found, a {@link UsernameNotFoundException} is thrown.</p>
     *
     * @param username the username identifying the user to retrieve
     * @return a {@link UserResponse} containing user data
     * @throws UsernameNotFoundException if no user is found with the specified username
     */
    public UserResponse findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
