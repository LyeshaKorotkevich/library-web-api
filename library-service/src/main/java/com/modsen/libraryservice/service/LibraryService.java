package com.modsen.libraryservice.service;

import com.modsen.libraryservice.model.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Service interface for managing library books.
 *
 * This interface defines the operations available for managing the library's book collection,
 * including retrieving available books, adding new books, and managing the borrowing and
 * returning of books.
 */
public interface LibraryService {

    /**
     * Retrieves a paginated list of available books in the library.
     *
     * @param pageable the pagination information, including page number and size.
     * @return a page of available {@link LibraryBook} objects.
     */
    Page<LibraryBook> getAvailableBooks(Pageable pageable);

    /**
     * Adds a new book to the library's collection.
     *
     * This method registers a book in the library system using the given book ID.
     *
     * @param bookId the ID of the book to add.
     * @return the added {@link LibraryBook} object.
     */
    LibraryBook addLibraryBook(Long bookId);

    /**
     * Registers a book as taken (borrowed) by a user.
     *
     * This method records the borrowing of a book and specifies the date by which
     * the book should be returned.
     *
     * @param bookId the ID of the book being borrowed.
     * @param returnBy the date and time by which the book should be returned.
     * @return the updated {@link LibraryBook} object with borrowing details.
     */
    LibraryBook registerBookTaken(Long bookId, LocalDateTime returnBy);

    /**
     * Registers the return of a borrowed book.
     *
     * This method updates the library's records to indicate that a book has been returned.
     *
     * @param bookId the ID of the book being returned.
     * @return the updated {@link LibraryBook} object reflecting its returned status.
     */
    LibraryBook registerBookReturned(Long bookId);
}
