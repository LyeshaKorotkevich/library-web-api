package com.modsen.bookservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents an author of books in the system.
 */
@Data
@Entity
@Table(name = "authors", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "surname", "birthDate"})
})
public class Author {

    /**
     * The unique identifier for the author.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The author's first name.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The author's surname.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String surname;

    /**
     * The author's date of birth.
     * Cannot be null.
     */
    @Column(nullable = false)
    private LocalDate birthDate;

    /**
     * The set of books written by the author.
     */
    @OneToMany(mappedBy = "author")
    private Set<Book> books;
}
