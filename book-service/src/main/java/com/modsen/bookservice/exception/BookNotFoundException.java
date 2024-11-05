package com.modsen.bookservice.exception;

public class BookNotFoundException extends RuntimeException {

    /**
     * The error message is formatted using the book's identifier.
     *
     * @param id - the identifier of the book
     */
    public BookNotFoundException(Long id) {
        super(String.format("Book with id: %s not found", id));
    }

    /**
     * The error message is formatted using the book's ISBN.
     *
     * @param isbn - the ISBN of the book
     */
    public BookNotFoundException(String isbn) {
        super(String.format("Book with isbn: %s not found", isbn));
    }
}
