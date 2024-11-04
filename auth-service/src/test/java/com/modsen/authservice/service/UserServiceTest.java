package com.modsen.authservice.service;

import com.modsen.authservice.dto.response.UserResponse;
import com.modsen.authservice.mapper.UserMapper;
import com.modsen.authservice.model.User;
import com.modsen.authservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void findUserByUsername_ShouldReturnUserResponse_WhenUserExists() {
        // given
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        UserResponse userResponse = new UserResponse(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // when
        UserResponse result = userService.findUserByUsername(username);

        // then
        assertEquals(username, result.username());
    }

    @Test
    void findUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {
        // given
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // when, then
        assertThrows(UsernameNotFoundException.class, () -> userService.findUserByUsername(username));
    }
}
