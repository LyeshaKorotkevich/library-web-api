package com.modsen.libraryservice.service;

import com.modsen.libraryservice.exception.LibraryBookNotFoundException;
import com.modsen.libraryservice.model.LibraryBook;
import com.modsen.libraryservice.repository.LibraryBookRepository;
import com.modsen.libraryservice.service.impl.LibraryServiceImpl;
import com.modsen.libraryservice.util.LibraryBookTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryServiceImplTest {

    @Mock
    private LibraryBookRepository libraryBookRepository;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private LibraryBook libraryBook;

    @BeforeEach
    void setUp() {
        libraryBook = LibraryBookTestData.builder().build().getLibraryBook();
    }

    @Test
    void getAvailableBooks_ShouldReturnListOfAvailableBooks() {
        // given
        List<LibraryBook> availableBooks = List.of(libraryBook);
        when(libraryBookRepository.findAllByTakenAtIsNull()).thenReturn(availableBooks);

        // when
        List<LibraryBook> result = libraryService.getAvailableBooks();

        // then
        assertEquals(1, result.size());
        assertEquals(availableBooks, result);
        verify(libraryBookRepository).findAllByTakenAtIsNull();
    }

    @Test
    void registerBookTaken_ShouldSaveAndReturnLibraryBookWithTakenAtAndReturnBy() {
        // given
        Long bookId = 4L;
        LocalDateTime returnBy = LocalDateTime.now().plusDays(7);
        LibraryBook takenBook = LibraryBookTestData.builder()
                .withBookId(bookId)
                .withTakenAt(LocalDateTime.now())
                .withReturnBy(returnBy)
                .build()
                .getLibraryBook();

        when(libraryBookRepository.findById(bookId)).thenReturn(Optional.of(libraryBook));
        when(libraryBookRepository.save(any(LibraryBook.class))).thenReturn(takenBook);

        // when
        LibraryBook result = libraryService.registerBookTaken(bookId, returnBy);

        // then
        assertNotNull(result);
        assertEquals(bookId, result.getBookId());
        assertNotNull(result.getTakenAt());
        assertEquals(returnBy, result.getReturnBy());
        verify(libraryBookRepository).save(any(LibraryBook.class));
    }

    @Test
    void registerBookTaken_ShouldThrowException_WhenBookNotFound() {
        // given
        Long bookId = 4L;
        LocalDateTime returnBy = LocalDateTime.now().plusDays(7);

        when(libraryBookRepository.findById(bookId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(LibraryBookNotFoundException.class, () -> {
            libraryService.registerBookTaken(bookId, returnBy);
        });
    }

    @Test
    void registerBookReturned_ShouldResetBookStatus() {
        // given
        Long bookId = 1L;
        libraryBook.setTakenAt(LocalDateTime.now());
        libraryBook.setReturnBy(LocalDateTime.now().plusDays(7));

        when(libraryBookRepository.findById(bookId)).thenReturn(Optional.of(libraryBook));
        when(libraryBookRepository.save(any(LibraryBook.class))).thenReturn(libraryBook);

        // when
        LibraryBook result = libraryService.registerBookReturned(bookId);

        // then
        assertNotNull(result);
        assertEquals(bookId, result.getBookId());
        assertNull(result.getTakenAt());
        assertNull(result.getReturnBy());
        verify(libraryBookRepository).save(any(LibraryBook.class));
    }

    @Test
    void registerBookReturned_ShouldThrowException_WhenBookNotFound() {
        // given
        Long bookId = 4L;

        when(libraryBookRepository.findById(bookId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(LibraryBookNotFoundException.class, () -> {
            libraryService.registerBookReturned(bookId);
        });
    }
}