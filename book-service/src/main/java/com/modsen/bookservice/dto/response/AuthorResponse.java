package com.modsen.bookservice.dto.response;

import java.time.LocalDate;

/**
 * Represents the response data for an author.
 *
 * This record is used for transferring author information from the server
 * to the client after querying or retrieving author details. It contains
 * the author's unique identifier, name, surname, and birth date.
 *
 * @param id        the unique identifier of the author
 * @param name      the name of the author
 * @param surname   the surname of the author
 * @param birthDate the birth date of the author
 */
public record AuthorResponse(
        Long id,
        String name,
        String surname,
        LocalDate birthDate
) {
}
