package com.modsen.authservice.mapper;

import com.modsen.authservice.dto.response.UserResponse;
import com.modsen.authservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
}
