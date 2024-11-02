package com.modsen.bookservice.dto.response;

import com.modsen.bookservice.model.Genre;

import java.util.Set;

public record BookResponse(
        Long id,
        String isbn,
        String title,
        Genre genre,
        String description,
        AuthorResponse author
) {
}
