package com.modsen.bookservice.mapper;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.AuthorResponse;
import com.modsen.bookservice.dto.response.BookResponse;
import com.modsen.bookservice.model.Author;
import com.modsen.bookservice.model.Book;
import com.modsen.bookservice.util.BookTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;
    private BookTestData bookTestData;

    @BeforeEach
    void setUp() {
        bookTestData = BookTestData.builder().build();
    }

    @Test
    void shouldMapBookToBookResponse() {
        // given
        Book book = bookTestData.getBook();

        // when
        BookResponse bookResponse = bookMapper.toResponse(book);

        // then
        assertThat(bookResponse.id()).isEqualTo(book.getId());
        assertThat(bookResponse.isbn()).isEqualTo(book.getIsbn());
        assertThat(bookResponse.title()).isEqualTo(book.getTitle());
        assertThat(bookResponse.genre()).isEqualTo(book.getGenre());
        assertThat(bookResponse.description()).isEqualTo(book.getDescription());

        AuthorResponse authorResponse = bookResponse.author();
        Author author = book.getAuthor();
        assertThat(authorResponse.id()).isEqualTo(author.getId());
        assertThat(authorResponse.name()).isEqualTo(author.getName());
        assertThat(authorResponse.surname()).isEqualTo(author.getSurname());
        assertThat(authorResponse.birthDate()).isEqualTo(author.getBirthDate());
    }

    @Test
    void shouldMapBookRequestToBook() {
        // given
        BookRequest bookRequest = bookTestData.getRequest();

        // when
        Book book = bookMapper.toEntity(bookRequest);

        // then
        assertThat(book.getIsbn()).isEqualTo(bookRequest.isbn());
        assertThat(book.getTitle()).isEqualTo(bookRequest.title());
        assertThat(book.getGenre()).isEqualTo(bookRequest.genre());
        assertThat(book.getDescription()).isEqualTo(bookRequest.description());

        Author author = book.getAuthor();
        AuthorRequest authorRequest = bookRequest.author();
        assertThat(author.getName()).isEqualTo(authorRequest.name());
        assertThat(author.getSurname()).isEqualTo(authorRequest.surname());
        assertThat(author.getBirthDate()).isEqualTo(authorRequest.birthDate());
    }
}