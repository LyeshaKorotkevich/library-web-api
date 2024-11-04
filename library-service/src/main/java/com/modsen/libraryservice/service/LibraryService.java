package com.modsen.libraryservice.service;

import com.modsen.libraryservice.model.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface LibraryService {

    Page<LibraryBook> getAvailableBooks(Pageable pageable);

    LibraryBook addLibraryBook(Long bookId);

    LibraryBook registerBookTaken(Long bookId, LocalDateTime returnBy);

    LibraryBook registerBookReturned(Long bookId);
}
