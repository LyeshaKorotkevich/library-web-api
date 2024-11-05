package com.modsen.authservice.mapper;

import com.modsen.authservice.dto.response.UserResponse;
import com.modsen.authservice.model.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting {@link User} entities to {@link UserResponse} DTOs.
 * Utilizes MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserResponse} DTO.
     *
     * @param user the {@link User} entity to convert
     * @return a {@link UserResponse} DTO containing user data
     */
    UserResponse toResponse(User user);
}
