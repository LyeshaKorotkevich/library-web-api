package com.modsen.libraryservice.controller;

import com.modsen.libraryservice.model.LibraryBook;
import com.modsen.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/library")
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/available-books")
    public ResponseEntity<List<LibraryBook>> getAvailableBooks() {
        return ResponseEntity.ok(libraryService.getAvailableBooks());
    }

    @PostMapping("/take/{bookId}")
    public ResponseEntity<LibraryBook> takeBook(@PathVariable Long bookId, @RequestBody LocalDateTime returnBy) {
        return ResponseEntity.ok(libraryService.registerBookTaken(bookId, returnBy));
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<LibraryBook> returnBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.registerBookReturned(bookId));
    }
}

