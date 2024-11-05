package com.modsen.libraryservice.filter;

import com.modsen.libraryservice.dto.response.UserResponse;
import com.modsen.libraryservice.security.JwtTokenUtil;
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
 * Filter for JWT authentication in the application.
 *
 * This filter intercepts incoming requests to validate the JWT token present in the
 * Authorization header, fetches the associated user details, and sets the authentication
 * in the Security context if the token is valid.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final RestTemplate restTemplate;

    /**
     * This method is invoked for every request to filter the incoming requests.
     * It checks for the presence of a JWT token, validates it, and retrieves
     * user details from the authentication service.
     *
     * @param request  the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param filterChain the filter chain to pass the request and response
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during the request processing
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
     * Retrieves the JWT token from the Authorization header of the request.
     *
     * @param request the incoming HTTP request
     * @return the JWT token as a String, or null if not found
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * Converts a UserResponse object to a UserDetails object for Spring Security.
     *
     * @param userResponse the UserResponse containing user information
     * @return a UserDetails object representing the authenticated user
     */
    private UserDetails toUserDetails(UserResponse userResponse) {
        return new User(
                userResponse.username(),
                "",
                Collections.emptyList()
        );
    }
}
