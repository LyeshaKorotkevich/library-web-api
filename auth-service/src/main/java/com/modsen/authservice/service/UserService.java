package com.modsen.authservice.service;

import com.modsen.authservice.dto.response.UserResponse;
import com.modsen.authservice.mapper.UserMapper;
import com.modsen.authservice.model.User;
import com.modsen.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
