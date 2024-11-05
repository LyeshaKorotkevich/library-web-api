package com.modsen.bookservice.dto.response;

import com.modsen.bookservice.model.Genre;

/**
 * Represents the response data for a book.
 *
 * This record is used for transferring book information from the server
 * to the client after querying or retrieving book details. It contains
 * the book's unique identifier, ISBN, title, genre, description, and
 * information about the author.
 *
 * @param id          the unique identifier of the book
 * @param isbn        the International Standard Book Number of the book
 * @param title       the title of the book
 * @param genre       the genre of the book
 * @param description a brief description of the book
 * @param author      the author information of the book represented by an {@link AuthorResponse}
 */
public record BookResponse(
        Long id,
        String isbn,
        String title,
        Genre genre,
        String description,
        AuthorResponse author
) {
}
