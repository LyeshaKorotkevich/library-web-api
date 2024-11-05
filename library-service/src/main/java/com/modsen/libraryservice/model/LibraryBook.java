package com.modsen.libraryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a book in the library's inventory.
 *
 * This entity maps to the "library_books" table in the database and contains information
 * about the book's availability status, including when it was taken and when it is due to be returned.
 */
@Data
@Entity
@Table(name = "library_books")
public class LibraryBook {

    /**
     * The unique identifier for the book.
     * This ID corresponds to the ID of the book in the library's collection.
     */
    @Id
    private Long bookId;

    /**
     * The timestamp indicating when the book was taken out.
     * If the book is not currently checked out, this value will be null.
     */
    private LocalDateTime takenAt;

    /**
     * The timestamp indicating when the book is due to be returned.
     * If the book is not currently checked out, this value will be null.
     */
    private LocalDateTime returnBy;
}
