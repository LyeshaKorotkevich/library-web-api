package com.modsen.libraryservice.service;

import com.modsen.libraryservice.model.LibraryBook;

import java.time.LocalDateTime;
import java.util.List;

public interface LibraryService {

    List<LibraryBook> getAvailableBooks();

    LibraryBook addLibraryBook(Long bookId);

    LibraryBook registerBookTaken(Long bookId, LocalDateTime returnBy);

    LibraryBook registerBookReturned(Long bookId);
}
