package com.modsen.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;
import com.modsen.bookservice.exception.BookNotFoundException;
import com.modsen.bookservice.security.JwtTokenUtil;
import com.modsen.bookservice.service.BookService;
import com.modsen.bookservice.util.BookTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private RestTemplate restTemplate;

    private BookTestData bookTestData;
    private final String url = "/api/books";

    @BeforeEach
    void setUp() {
        bookTestData = BookTestData.builder().build();
    }

    @Test
    @WithMockUser()
    void getAllBooks_ShouldReturnPageOfBooks() throws Exception {
        BookResponse bookResponse = bookTestData.getResponse();
        Page<BookResponse> bookResponsePage = new PageImpl<>(List.of(bookResponse), PageRequest.of(0, 10), 1);

        Mockito.when(bookService.getAllBooks(Mockito.any())).thenReturn(bookResponsePage);

        mockMvc.perform(get(url)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.totalElements").value(1),
                        jsonPath("$.content[0].id").value(bookResponse.id()),
                        jsonPath("$.content[0].isbn").value(bookResponse.isbn()),
                        jsonPath("$.content[0].title").value(bookResponse.title())
                );
    }

    @Test
    @WithMockUser()
    void getBookById_ShouldReturnBook_WhenBookExists() throws Exception {
        BookResponse bookResponse = bookTestData.getResponse();
        Mockito.when(bookService.getBookById(1L)).thenReturn(bookResponse);

        mockMvc.perform(get(url + "/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(bookResponse.id()),
                        jsonPath("$.isbn").value(bookResponse.isbn()),
                        jsonPath("$.title").value(bookResponse.title())
                );
    }

    @Test
    @WithMockUser()
    void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        Mockito.when(bookService.getBookById(9999L)).thenThrow(new BookNotFoundException(9999L));

        mockMvc.perform(get(url + "/9999").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser()
    void addBook_ShouldReturnCreatedBook_WhenBookIsValid() throws Exception {
        BookRequest bookRequest = bookTestData.getRequest();
        BookResponse bookResponse = bookTestData.getResponse();

        Mockito.when(bookService.addBook(any(BookRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(post(url)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
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
    @WithMockUser()
    void addBook_ShouldReturnBadRequest_WhenBookIsInvalid() throws Exception {
        BookRequest invalidBookRequest = BookTestData.builder().withTitle("").build().getRequest();

        mockMvc.perform(post(url)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBookRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title cannot be blank"));
    }

    @Test
    @WithMockUser()
    void updateBook_ShouldReturnUpdatedBook_WhenBookIsValid() throws Exception {
        BookRequest bookRequest = bookTestData.getRequest();
        BookResponse bookResponse = bookTestData.getResponse();

        Mockito.when(bookService.updateBook(eq(1L), any(BookRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(put(url + "/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(bookResponse.id()),
                        jsonPath("$.title").value(bookResponse.title())
                );
    }

    @Test
    @WithMockUser()
    void deleteBook_ShouldReturnNoContent_WhenBookExists() throws Exception {
        mockMvc.perform(delete(url + "/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }
}
