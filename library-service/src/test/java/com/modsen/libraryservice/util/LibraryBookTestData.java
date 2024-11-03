package com.modsen.libraryservice.util;

import com.modsen.libraryservice.model.LibraryBook;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
public class LibraryBookTestData {

    @Builder.Default
    private Long bookId = 1L;

    @Builder.Default
    private LocalDateTime takenAt = null;

    @Builder.Default
    private java.time.LocalDateTime returnBy = null;

    public LibraryBook getLibraryBook() {
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setBookId(bookId);
        libraryBook.setTakenAt(takenAt);
        libraryBook.setReturnBy(returnBy);
        return libraryBook;
    }
}
