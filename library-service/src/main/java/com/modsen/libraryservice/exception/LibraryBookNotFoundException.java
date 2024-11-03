package com.modsen.libraryservice.exception;

public class LibraryBookNotFoundException extends RuntimeException {

    /**
     * Сообщение об ошибке формируется с учетом идентификатора книги.
     *
     * @param id - идентификатор книги
     */
    public LibraryBookNotFoundException(Long id) {
        super(String.format("Book with id: %s not found", id));
    }
}
