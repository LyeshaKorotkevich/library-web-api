package com.modsen.bookservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Represents a request to create or update an author.
 *
 * This record is used for data transfer between the client and server
 * when creating or updating author information. It includes the author's
 * name, surname, and birth date, with appropriate validation constraints.
 *
 * @param name      the name of the author
 * @param surname   the surname of the author
 * @param birthDate the birth date of the author
 */
public record AuthorRequest(
        @NotBlank
        String name,

        @NotBlank
        String surname,

        @NotNull
        LocalDate birthDate
) {

}
