package com.modsen.libraryservice.controller;

import com.modsen.libraryservice.model.LibraryBook;
import com.modsen.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDateTime;

/**
 * REST controller for managing library books.
 *
 * This controller provides endpoints for retrieving available books,
 * taking a book (registering it as checked out), and returning a book
 * (registering it as checked in).
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService libraryService;

    /**
     * Retrieves a paginated list of available books in the library.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of items per page (default is 10)
     * @return a ResponseEntity containing a page of available LibraryBook objects
     */
    @Operation(summary = "Get available books",
            description = "Retrieves a paginated list of books that are currently available in the library.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available books"),
            @ApiResponse(responseCode = "400", description = "Invalid page number or size")
    })
    @GetMapping("/available-books")
    public ResponseEntity<Page<LibraryBook>> getAvailableBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LibraryBook> availableBooks = libraryService.getAvailableBooks(pageable);
        return ResponseEntity.ok(availableBooks);
    }

    /**
     * Registers a book as taken (checked out) by updating its status.
     *
     * @param bookId the ID of the book to take
     * @param returnBy the date and time by which the book should be returned
     * @return a ResponseEntity containing the updated LibraryBook object
     */
    @Operation(summary = "Take a book",
            description = "Registers a book as taken (checked out) with the specified return date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the book as taken"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PostMapping("/take/{bookId}")
    public ResponseEntity<LibraryBook> takeBook(
            @Parameter(description = "ID of the book to take", required = true) @PathVariable Long bookId,
            @Parameter(description = "Date and time by which the book should be returned", required = true) @RequestBody LocalDateTime returnBy) {
        return ResponseEntity.ok(libraryService.registerBookTaken(bookId, returnBy));
    }

    /**
     * Registers a book as returned (checked in) by updating its status.
     *
     * @param bookId the ID of the book to return
     * @return a ResponseEntity containing the updated LibraryBook object
     */
    @Operation(summary = "Return a book",
            description = "Registers a book as returned (checked in).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the book as returned"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PostMapping("/return/{bookId}")
    public ResponseEntity<LibraryBook> returnBook(@Parameter(description = "ID of the book to return", required = true) @PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.registerBookReturned(bookId));
    }
}
