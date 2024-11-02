package com.modsen.bookservice.exception;

public class BookNotFoundException extends RuntimeException {

    /**
     * Сообщение об ошибке формируется с учетом идентификатора книги.
     *
     * @param id - идентификатор книги
     */
    public BookNotFoundException(Long id) {
        super(String.format("Book with id: %s not found", id));
    }

    /**
     * Сообщение об ошибке формируется с учетом isbn книги.
     *
     * @param isbn - isbn книги
     */
    public BookNotFoundException(String isbn) {
        super(String.format("Book with isbn: %s not found", isbn));
    }
}
