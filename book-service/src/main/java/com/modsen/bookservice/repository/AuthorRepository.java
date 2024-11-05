package com.modsen.bookservice.repository;

import com.modsen.bookservice.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link Author} entities.
 * This interface extends JpaRepository to provide CRUD operations
 * and custom query methods for authors in the database.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Finds an author by their name, surname, and birth date.
     *
     * @param name     the name of the author
     * @param surname  the surname of the author
     * @param birthDate the birth date of the author
     * @return an Optional containing the found Author, or empty if not found
     */
    Optional<Author> findByNameAndSurnameAndBirthDate(String name, String surname, LocalDate birthDate);
}
