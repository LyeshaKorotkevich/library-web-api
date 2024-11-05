package com.modsen.libraryservice.service.impl;

import com.modsen.libraryservice.exception.LibraryBookNotFoundException;
import com.modsen.libraryservice.model.LibraryBook;
import com.modsen.libraryservice.repository.LibraryBookRepository;
import com.modsen.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of the {@link LibraryService} interface.
 *
 * This service handles the management of library books, including retrieving available books,
 * adding new books, and registering the borrowing and returning of books.
 */
@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final LibraryBookRepository libraryBookRepository;

    @Override
    public Page<LibraryBook> getAvailableBooks(Pageable pageable) {
        return libraryBookRepository.findAllByTakenAtIsNull(pageable);
    }

    @Override
    public LibraryBook addLibraryBook(Long bookId) {
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setBookId(bookId);
        return libraryBookRepository.save(libraryBook);
    }

    @Override
    public LibraryBook registerBookTaken(Long bookId, LocalDateTime returnBy) {
        LibraryBook book = libraryBookRepository
                .findById(bookId)
                .filter(b -> b.getReturnBy() == null)
                .orElseThrow(() -> new LibraryBookNotFoundException(bookId));

        book.setTakenAt(LocalDateTime.now());
        book.setReturnBy(returnBy);
        return libraryBookRepository.save(book);
    }

    @Override
    public LibraryBook registerBookReturned(Long bookId) {
        LibraryBook book = libraryBookRepository
                .findById(bookId)
                .orElseThrow(() -> new LibraryBookNotFoundException(bookId));

        book.setTakenAt(null);
        book.setReturnBy(null);
        return libraryBookRepository.save(book);
    }
}

