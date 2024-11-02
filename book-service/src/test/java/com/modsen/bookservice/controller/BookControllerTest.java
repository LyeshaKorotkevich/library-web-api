package com.modsen.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;
import com.modsen.bookservice.exception.BookNotFoundException;
import com.modsen.bookservice.model.Genre;
import com.modsen.bookservice.service.BookService;
import com.modsen.bookservice.util.BookTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private BookTestData bookTestData;
    private final String url = "/api/books";

    @BeforeEach
    void setUp() {
        bookTestData = BookTestData.builder().build();
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        BookResponse bookResponse = bookTestData.getResponse();
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(bookResponse));

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[0].id").value(bookResponse.id()),
                        jsonPath("$[0].isbn").value(bookResponse.isbn()),
                        jsonPath("$[0].title").value(bookResponse.title())
                );
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() throws Exception {
        BookResponse bookResponse = bookTestData.getResponse();
        Mockito.when(bookService.getBookById(1L)).thenReturn(bookResponse);

        mockMvc.perform(get(url + "/1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(bookResponse.id()),
                        jsonPath("$.isbn").value(bookResponse.isbn()),
                        jsonPath("$.title").value(bookResponse.title())
                );
    }

    @Test
    void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        Mockito.when(bookService.getBookById(9999L)).thenThrow(new BookNotFoundException(9999L));

        mockMvc.perform(get(url + "/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBook_ShouldReturnCreatedBook_WhenBookIsValid() throws Exception {
        BookRequest bookRequest = bookTestData.getRequest();
        BookResponse bookResponse = bookTestData.getResponse();

        Mockito.when(bookService.addBook(any(BookRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(bookResponse.id()),
                        jsonPath("$.isbn").value(bookResponse.isbn()),
                        jsonPath("$.title").value(bookResponse.title())
                );
    }

    @Test
    void addBook_ShouldReturnBadRequest_WhenBookIsInvalid() throws Exception {
        BookRequest invalidBookRequest = BookTestData.builder().withTitle("").build().getRequest();

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBookRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title cannot be blank"));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook_WhenBookIsValid() throws Exception {
        BookRequest bookRequest = bookTestData.getRequest();
        BookResponse bookResponse = bookTestData.getResponse();

        Mockito.when(bookService.updateBook(eq(1L), any(BookRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(put(url + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(bookResponse.id()),
                        jsonPath("$.title").value(bookResponse.title())
                );
    }

    @Test
    void deleteBook_ShouldReturnNoContent_WhenBookExists() throws Exception {
        mockMvc.perform(delete(url + "/1"))
                .andExpect(status().isNoContent());
    }
}
