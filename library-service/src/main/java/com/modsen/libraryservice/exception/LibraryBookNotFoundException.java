package com.modsen.libraryservice.exception;

/**
 * Custom exception thrown when a requested library book is not found.
 *
 * This exception is typically used in service layers to indicate that an operation
 * involving a library book cannot proceed because the specified book does not exist.
 */
public class LibraryBookNotFoundException extends RuntimeException {

    /**
     * Constructs a new LibraryBookNotFoundException with a detail message.
     *
     * The message is constructed using the provided book ID, which is included to
     * specify which book could not be found.
     *
     * @param id the ID of the book that was not found
     */
    public LibraryBookNotFoundException(Long id) {
        super(String.format("Book with id: %s not found", id));
    }
}
