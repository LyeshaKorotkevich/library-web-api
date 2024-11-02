package com.modsen.bookservice.dto.request;

import com.modsen.bookservice.model.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
