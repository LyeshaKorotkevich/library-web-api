package com.modsen.libraryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.libraryservice.exception.LibraryBookNotFoundException;
import com.modsen.libraryservice.model.LibraryBook;
import com.modsen.libraryservice.security.JwtTokenUtil;
import com.modsen.libraryservice.service.LibraryService;
import com.modsen.libraryservice.util.LibraryBookTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private RestTemplate restTemplate;

    private LibraryBook libraryBook;
    private final String url = "/api/library";

    @BeforeEach
    void setUp() {
        libraryBook = LibraryBookTestData.builder().build().getLibraryBook();
    }

    @Test
    @WithMockUser()
    void testGetAvailableBooks() throws Exception {
        Page<LibraryBook> libraryBooks = new PageImpl<>(List.of(new LibraryBook()), PageRequest.of(0, 10), 1);
        when(libraryService.getAvailableBooks(any(Pageable.class))).thenReturn(libraryBooks);

        mockMvc.perform(get(url + "/available-books")
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @WithMockUser()
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
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(returnBy)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.bookId").value(takenBook.getBookId()),
                        jsonPath("$.takenAt").value(containsString(takenBook.getTakenAt().toString().substring(0, 22))),
                        jsonPath("$.returnBy").value(takenBook.getReturnBy().toString())
                );
    }

    @Test
    @WithMockUser()
    void takeBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        Long bookId = 1L;
        LocalDateTime returnBy = LocalDateTime.now().plusDays(7);

        when(libraryService.registerBookTaken(eq(bookId), any(LocalDateTime.class)))
                .thenThrow(new LibraryBookNotFoundException(bookId));

        mockMvc.perform(post(url + "/take/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(returnBy)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser()
    void returnBook_ShouldReturnBook_WhenBookIsReturned() throws Exception {
        Long bookId = 1L;

        when(libraryService.registerBookReturned(bookId)).thenReturn(libraryBook);

        mockMvc.perform(post(url + "/return/" + bookId).with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.bookId").value(libraryBook.getBookId()),
                        jsonPath("$.takenAt").value(nullValue()),
                        jsonPath("$.returnBy").value(nullValue())
                );
    }

    @Test
    @WithMockUser()
    void returnBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        Long bookId = 1L;

        when(libraryService.registerBookReturned(bookId))
                .thenThrow(new LibraryBookNotFoundException(bookId));

        mockMvc.perform(post(url + "/return/" + bookId).with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }
}