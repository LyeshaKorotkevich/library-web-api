package com.modsen.bookservice.service.impl;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.mapper.AuthorMapper;
import com.modsen.bookservice.model.Author;
import com.modsen.bookservice.repository.AuthorRepository;
import com.modsen.bookservice.util.AuthorTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private AuthorRequest authorRequest;

    @BeforeEach
    void setUp() {
        author = AuthorTestData.builder().build().getAuthor();
        authorRequest = AuthorTestData.builder().build().getRequest();
    }

    @Test
    void getOrCreateAuthor_ShouldReturnAuthor_WhenAuthorExists() {
        // given
        when(authorRepository.findByNameAndSurnameAndBirthDate(
                authorRequest.name(), authorRequest.surname(), authorRequest.birthDate()))
                .thenReturn(Optional.of(author));

        // when
        Author result = authorService.getOrCreateAuthor(authorRequest);

        // then
        assertEquals(author, result);
        verify(authorRepository).findByNameAndSurnameAndBirthDate(
                authorRequest.name(), authorRequest.surname(), authorRequest.birthDate());
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void getOrCreateAuthor_ShouldReturnAuthor_WhenAuthorNotExists() {
        // given
        Author newAuthor = AuthorTestData.builder().withId(null).build().getAuthor();
        Author savedAuthor = AuthorTestData.builder().withId(2L).build().getAuthor();

        when(authorRepository.findByNameAndSurnameAndBirthDate(
                authorRequest.name(), authorRequest.surname(), authorRequest.birthDate()))
                .thenReturn(Optional.empty());
        when(authorMapper.toEntity(authorRequest)).thenReturn(newAuthor);
        when(authorRepository.save(newAuthor)).thenReturn(savedAuthor);

        // when
        Author result = authorService.getOrCreateAuthor(authorRequest);

        // then
        assertEquals(savedAuthor, result);
        verify(authorRepository).findByNameAndSurnameAndBirthDate(
                authorRequest.name(), authorRequest.surname(), authorRequest.birthDate());
        verify(authorMapper).toEntity(authorRequest);
        verify(authorRepository).save(newAuthor);
    }
}