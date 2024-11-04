package com.modsen.authservice.security;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        // given
        String username = "testuser";
        when(authentication.getName()).thenReturn(username);

        // when
        String token = jwtTokenUtil.generateToken(authentication);

        // then
        assertNotNull(token);
        assertTrue(jwtTokenUtil.validateToken(token));
    }

    @Test
    void getUsername_ShouldReturnCorrectUsername() {
        // given
        String username = "testuser";
        when(authentication.getName()).thenReturn(username);
        String token = jwtTokenUtil.generateToken(authentication);

        // when
        String extractedUsername = jwtTokenUtil.getUsername(token);

        // then
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        // given
        String username = "testuser";
        when(authentication.getName()).thenReturn(username);
        String token = jwtTokenUtil.generateToken(authentication);

        // when, then
        assertTrue(jwtTokenUtil.validateToken(token));
    }

    @Test
    void validateToken_ShouldThrowExceptionForInvalidToken() {
        // given
        String invalidToken = "invalidToken";

        // when, then
        assertThrows(MalformedJwtException.class, () -> jwtTokenUtil.validateToken(invalidToken));
    }
}