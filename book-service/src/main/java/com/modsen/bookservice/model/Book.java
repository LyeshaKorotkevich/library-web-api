package com.modsen.bookservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents a book in the system.
 */
@Data
@Entity
@Table(name = "books")
public class Book {

    /**
     * The unique identifier for the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The International Standard Book Number (ISBN) of the book.
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String isbn;

    /**
     * The title of the book.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String title;

    /**
     * The genre of the book.
     * Cannot be null.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /**
     * A brief description of the book.
     * Can be null.
     */
    private String description;

    /**
     * The author of the book.
     * A book must have one author.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
