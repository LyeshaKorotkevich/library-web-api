package com.modsen.bookservice.controller;

import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;
import com.modsen.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * REST controller for managing books.
 *
 * This controller exposes endpoints for creating, retrieving, updating, and deleting
 * books in the book service. It delegates the actual business logic to the
 * {@link BookService} class and returns appropriate HTTP responses.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * Retrieves all books with pagination.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of books per page (default is 10)
     * @return a {@link ResponseEntity} containing a page of {@link BookResponse} objects
     */
    @Operation(summary = "Get all books", description = "Retrieves a paginated list of all books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(
            @ParameterObject @RequestParam(defaultValue = "0") int page,
            @ParameterObject @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookResponse> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(books);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return a {@link ResponseEntity} containing the {@link BookResponse} object
     */
    @Operation(summary = "Get book by ID", description = "Retrieves a book by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of book"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn the ISBN of the book to retrieve
     * @return a {@link ResponseEntity} containing the {@link BookResponse} object
     */
    @Operation(summary = "Get book by ISBN", description = "Retrieves a book by its ISBN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of book"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    /**
     * Adds a new book.
     *
     * @param bookRequest the details of the book to add
     * @return a {@link ResponseEntity} containing the added {@link BookResponse} object
     */
    @Operation(summary = "Add a new book", description = "Adds a new book to the collection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful addition of book"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.addBook(bookRequest));
    }

    /**
     * Updates an existing book.
     *
     * @param id the ID of the book to update
     * @param bookRequest the updated details of the book
     * @return a {@link ResponseEntity} containing the updated {@link BookResponse} object
     */
    @Operation(summary = "Update an existing book", description = "Updates the details of an existing book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update of book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     * @return a {@link ResponseEntity} with no content
     */
    @Operation(summary = "Delete a book", description = "Deletes a book by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of book"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
