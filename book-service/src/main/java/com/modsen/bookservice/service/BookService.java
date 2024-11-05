package com.modsen.bookservice.service;

import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing books in the book service application.
 * Provides methods for performing operations related to books.
 */
public interface BookService {

    /**
     * Retrieves a paginated list of all books.
     *
     * @param pageable the pagination information
     * @return a page of BookResponse containing the list of books
     */
    Page<BookResponse> getAllBooks(Pageable pageable);

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param id the unique identifier of the book
     * @return the BookResponse containing the book details
     */
    BookResponse getBookById(Long id);

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return the BookResponse containing the book details
     */
    BookResponse getBookByIsbn(String isbn);

    /**
     * Adds a new book based on the provided request details.
     *
     * @param bookRequest the request containing the book details
     * @return the BookResponse containing the newly added book details
     */
    BookResponse addBook(BookRequest bookRequest);

    /**
     * Updates the details of an existing book.
     *
     * @param id the unique identifier of the book to update
     * @param bookRequest the request containing the updated book details
     * @return the BookResponse containing the updated book details
     */
    BookResponse updateBook(Long id, BookRequest bookRequest);

    /**
     * Deletes a book by its unique identifier.
     *
     * @param id the unique identifier of the book to delete
     */
    void deleteBook(Long id);
}
