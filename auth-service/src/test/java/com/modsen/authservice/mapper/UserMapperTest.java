package com.modsen.authservice.mapper;

import com.modsen.authservice.dto.response.UserResponse;
import com.modsen.authservice.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toResponse_ShouldMapUserToUserResponse() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");

        // when
        UserResponse userResponse = userMapper.toResponse(user);

        // then
        assertEquals(user.getUsername(), userResponse.username());
    }
}