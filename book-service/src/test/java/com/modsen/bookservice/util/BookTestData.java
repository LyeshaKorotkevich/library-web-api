package com.modsen.bookservice.util;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.AuthorResponse;
import com.modsen.bookservice.dto.response.BookResponse;
import com.modsen.bookservice.model.Author;
import com.modsen.bookservice.model.Book;
import com.modsen.bookservice.model.Genre;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class BookTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String isbn = "123456789";

    @Builder.Default
    private String title = "Default Title";

    @Builder.Default
    private Genre genre = Genre.FICTION;

    @Builder.Default
    private String description = "Default Description";

    @Builder.Default
    private Author author = AuthorTestData
            .builder()
            .build()
            .getAuthor();


    public Book getBook() {
        Book book = new Book();
        book.setId(id);
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setGenre(genre);
        book.setDescription(description);
        book.setAuthor(author);
        return book;
    }

    public BookRequest getRequest() {
        AuthorRequest authorRequests = AuthorTestData.builder()
                .withName(author.getName())
                .withSurname(author.getSurname())
                .withBirthDate(author.getBirthDate())
                .build().getRequest();

        return new BookRequest(isbn, title, genre, description, authorRequests);
    }

    public BookResponse getResponse() {
        AuthorResponse authorResponses = AuthorTestData.builder()
                .withName(author.getName())
                .withSurname(author.getSurname())
                .withBirthDate(author.getBirthDate())
                .build().getResponse();

        return new BookResponse(id, isbn, title, genre, description, authorResponses);
    }
}
