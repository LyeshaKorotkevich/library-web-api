package com.modsen.bookservice.service.impl;

import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;
import com.modsen.bookservice.exception.BookNotFoundException;
import com.modsen.bookservice.mapper.BookMapper;
import com.modsen.bookservice.model.Author;
import com.modsen.bookservice.model.Book;
import com.modsen.bookservice.repository.BookRepository;
import com.modsen.bookservice.service.AuthorService;
import com.modsen.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the BookService interface.
 * Provides methods for managing books in the book service application.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;

    private final KafkaTemplate<String, Long> kafkaTemplate;

    @Override
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository
                .findAll(pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public BookResponse getBookById(Long id) {
        return bookRepository
                .findById(id)
                .map(bookMapper::toResponse)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public BookResponse getBookByIsbn(String isbn) {
        return bookRepository
                .findByIsbn(isbn)
                .map(bookMapper::toResponse)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    @Override
    @Transactional
    public BookResponse addBook(BookRequest bookRequest) {
        Book book = bookMapper.toEntity(bookRequest);
        Author author = authorService.getOrCreateAuthor(bookRequest.author());
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);
        Long bookId = savedBook.getId();

        kafkaTemplate.send("book-topic", bookId);

        return bookMapper.toResponse(savedBook);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, BookRequest updatedBookRequest) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBookRequest.title());
                    book.setIsbn(updatedBookRequest.isbn());
                    book.setGenre(updatedBookRequest.genre());
                    book.setDescription(updatedBookRequest.description());

                    Author author = authorService.getOrCreateAuthor(updatedBookRequest.author());

                    book.setAuthor(author);
                    return bookMapper.toResponse(bookRepository.save(book));
                }).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.delete(book);
    }
}
