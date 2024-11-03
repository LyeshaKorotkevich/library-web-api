package com.modsen.bookservice.mapper;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.dto.response.AuthorResponse;
import com.modsen.bookservice.model.Author;
import com.modsen.bookservice.util.AuthorTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


class AuthorMapperTest {

    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    private AuthorTestData authorTestData;

    @BeforeEach
    void setUp() {
        authorTestData = AuthorTestData.builder().build();
    }

    @Test
    void shouldMapAuthorToAuthorResponse() {
        // given
        Author author = authorTestData.getAuthor();

        // when
        AuthorResponse authorResponse = authorMapper.toResponse(author);

        // then
        assertThat(authorResponse.id()).isEqualTo(author.getId());
        assertThat(authorResponse.name()).isEqualTo(author.getName());
        assertThat(authorResponse.surname()).isEqualTo(author.getSurname());
        assertThat(authorResponse.birthDate()).isEqualTo(author.getBirthDate());
    }

    @Test
    void shouldMapAuthorRequestToAuthor() {
        // given
        AuthorRequest authorRequest = authorTestData.getRequest();

        // when
        Author author = authorMapper.toEntity(authorRequest);

        // then
        assertThat(author.getName()).isEqualTo(authorRequest.name());
        assertThat(author.getSurname()).isEqualTo(authorRequest.surname());
        assertThat(author.getBirthDate()).isEqualTo(authorRequest.birthDate());
    }
}