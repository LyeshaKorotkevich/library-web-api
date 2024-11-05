package com.modsen.authservice.service;

import com.modsen.authservice.model.User;
import com.modsen.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserDetailsService} interface
 * for loading user-specific data during authentication.
 *
 * <p>This service retrieves user details from the database
 * via the {@link UserRepository}.</p>
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Repository for accessing user data.
     */
    private final UserRepository userRepository;

    /**
     * Loads a user's data by their username.
     *
     * <p>This method is used by Spring Security to retrieve
     * user details when authenticating. If the user is not found,
     * a {@link UsernameNotFoundException} is thrown.</p>
     *
     * @param username the username identifying the user whose data is required
     * @return a fully populated {@link UserDetails} object with the user's information
     * @throws UsernameNotFoundException if no user is found with the provided username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
