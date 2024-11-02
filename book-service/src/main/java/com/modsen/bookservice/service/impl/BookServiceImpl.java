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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
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
        return bookMapper.toResponse(bookRepository.save(book));
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
