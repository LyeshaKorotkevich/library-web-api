package com.modsen.bookservice.dto.request;

import com.modsen.bookservice.model.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Represents a request to create or update a book.
 *
 * This record is used for transferring book data between the client and server
 * when creating or updating book information. It includes the book's ISBN,
 * title, genre, description, and the associated author information.

 * @param isbn       the ISBN of the book, must be a valid format
 * @param title      the title of the book, cannot be blank
 * @param genre      the genre of the book, can be null
 * @param description a description of the book, must not exceed 500 characters
 * @param author     the author of the book, encapsulated in an {@link AuthorRequest}
 */
public record BookRequest(
        @NotBlank(message = "ISBN cannot be blank")
        @Pattern(
                regexp = "^(97([89]))?[-\\s]?\\d{1,5}[-\\s]?\\d{1,7}[-\\s]?\\d{1,7}[-\\s]?(\\d|X)$",
                message = "Invalid ISBN format"
        )
        String isbn,

        @NotBlank(message = "Title cannot be blank")
        String title,

        Genre genre,

        @Size(max = 500, message = "Description should not exceed 500 characters")
        String description,

        AuthorRequest author
) {
}
