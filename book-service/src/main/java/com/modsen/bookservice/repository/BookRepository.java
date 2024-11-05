package com.modsen.bookservice.repository;

import com.modsen.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Book} entities.
 * This interface extends JpaRepository to provide basic CRUD operations
 * and custom query methods for books in the database.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds a book by its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return an Optional containing the found Book, or empty if no book with the specified ISBN is found
     */
    Optional<Book> findByIsbn(String isbn);
}
