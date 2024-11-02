package com.modsen.bookservice.service;

import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookResponse> getAllBooks();

    BookResponse getBookById(Long id);

    BookResponse getBookByIsbn(String isbn);

    BookResponse addBook(BookRequest bookRequest);

    BookResponse updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);
}
