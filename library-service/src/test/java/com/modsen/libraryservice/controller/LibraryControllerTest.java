package com.modsen.libraryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.libraryservice.exception.LibraryBookNotFoundException;
import com.modsen.libraryservice.model.LibraryBook;
import com.modsen.libraryservice.service.LibraryService;
import com.modsen.libraryservice.util.LibraryBookTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LibraryService libraryService;

    private LibraryBook libraryBook;
    private final String url = "/api/library";

    @BeforeEach
    void setUp() {
        libraryBook = LibraryBookTestData.builder().build().getLibraryBook();
    }

    @Test
    void getAvailableBooks_ShouldReturnListOfAvailableBooks() throws Exception {
        // given
        when(libraryService.getAvailableBooks()).thenReturn(List.of(libraryBook));

        // when, then
        mockMvc.perform(get(url + "/available-books"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[0].bookId").value(libraryBook.getBookId()),
                        jsonPath("$[0].takenAt").value(nullValue()),
                        jsonPath("$[0].returnBy").value(nullValue())
                );
    }

    @Test
    void takeBook_ShouldReturnBookWithTakenAtAndReturnBy_WhenBookIsTaken() throws Exception {
        Long bookId = 1L;
        LocalDateTime returnBy = LocalDateTime.now().plusDays(7);
        LibraryBook takenBook = LibraryBookTestData.builder()
                .withBookId(bookId)
                .withTakenAt(LocalDateTime.now())
                .withReturnBy(returnBy)
                .build()
                .getLibraryBook();

        when(libraryService.registerBookTaken(eq(bookId), any(LocalDateTime.class))).thenReturn(takenBook);

        mockMvc.perform(post(url + "/take/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnBy)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.bookId").value(takenBook.getBookId()),
                        jsonPath("$.takenAt").value(takenBook.getTakenAt().toString()),
                        jsonPath("$.returnBy").value(takenBook.getReturnBy().toString())
                );
    }

    @Test
    void takeBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        Long bookId = 1L;
        LocalDateTime returnBy = LocalDateTime.now().plusDays(7);

        when(libraryService.registerBookTaken(eq(bookId), any(LocalDateTime.class)))
                .thenThrow(new LibraryBookNotFoundException(bookId));

        mockMvc.perform(post(url + "/take/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnBy)))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnBook_ShouldReturnBook_WhenBookIsReturned() throws Exception {
        Long bookId = 1L;

        when(libraryService.registerBookReturned(bookId)).thenReturn(libraryBook);

        mockMvc.perform(post(url + "/return/" + bookId))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.bookId").value(libraryBook.getBookId()),
                        jsonPath("$.takenAt").value(nullValue()),
                        jsonPath("$.returnBy").value(nullValue())
                );
    }

    @Test
    void returnBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        Long bookId = 1L;

        when(libraryService.registerBookReturned(bookId))
                .thenThrow(new LibraryBookNotFoundException(bookId));

        mockMvc.perform(post(url + "/return/" + bookId))
                .andExpect(status().isNotFound());
    }
}