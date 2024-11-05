package com.modsen.bookservice.filter;

import com.modsen.bookservice.dto.response.UserResponse;
import com.modsen.bookservice.security.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filter for JWT authentication that validates the JWT token and sets the authentication context.
 * It retrieves user details from the authentication service based on the token's username.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate restTemplate;

    /**
     * Filters requests to validate the JWT token.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain for further processing of the request
     * @throws ServletException if an error occurs during the filter process
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (StringUtils.hasText(token) && jwtTokenUtil.validateToken(token)) {
            String username = jwtTokenUtil.getUsername(token);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = "http://auth-service:8082/api/users/" + username;
            ResponseEntity<UserResponse> userResponse = restTemplate.exchange(url, HttpMethod.GET, entity, UserResponse.class);
            if (userResponse.getBody() != null) {
                UserDetails userDetails = toUserDetails(userResponse.getBody());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header of the request.
     *
     * @param request the HTTP request
     * @return the extracted JWT token, or null if no valid token is found
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * Converts a UserResponse DTO to a UserDetails object.
     *
     * @param userResponse the UserResponse containing user information
     * @return a UserDetails object representing the user
     */
    private UserDetails toUserDetails(UserResponse userResponse) {
        return new User(
                userResponse.username(),
                "",
                Collections.emptyList()
        );
    }
}
