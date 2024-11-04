package com.modsen.bookservice.service;

import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponse> getAllBooks(Pageable pageable);

    BookResponse getBookById(Long id);

    BookResponse getBookByIsbn(String isbn);

    BookResponse addBook(BookRequest bookRequest);

    BookResponse updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);
}
