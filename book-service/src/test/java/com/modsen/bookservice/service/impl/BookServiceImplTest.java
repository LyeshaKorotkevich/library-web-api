package com.modsen.bookservice.service.impl;

import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;
import com.modsen.bookservice.exception.BookNotFoundException;
import com.modsen.bookservice.mapper.BookMapper;
import com.modsen.bookservice.model.Author;
import com.modsen.bookservice.model.Book;
import com.modsen.bookservice.repository.BookRepository;
import com.modsen.bookservice.service.AuthorService;
import com.modsen.bookservice.util.AuthorTestData;
import com.modsen.bookservice.util.BookTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private KafkaTemplate<String, Long> kafkaTemplate;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequest bookRequest;
    private Book book;
    private BookResponse bookResponse;

    @BeforeEach
    void setUp() {
        bookRequest = BookTestData.builder().build().getRequest();
        book = BookTestData.builder().build().getBook();
        bookResponse = BookTestData.builder().build().getResponse();
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        // given
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        // when
        List<BookResponse> result = bookService.getAllBooks();

        // then
        assertEquals(1, result.size());
        assertEquals(bookResponse, result.get(0));
        verify(bookRepository).findAll();
    }

    @Test
    void getBookById_ShouldReturnBookResponse_WhenBookExists() {
        // given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        // when
        BookResponse result = bookService.getBookById(1L);

        // then
        assertEquals(bookResponse, result);
        verify(bookRepository).findById(1L);
    }

    @Test
    void getBookById_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        // given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when, then
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository).findById(1L);
    }

    @Test
    void addBook_ShouldSaveBookAndReturnResponse() {
        // given
        when(bookMapper.toEntity(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        // when
        BookResponse result = bookService.addBook(bookRequest);

        // then
        assertEquals(bookResponse, result);
        verify(bookMapper).toEntity(bookRequest);
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook_ShouldUpdateAndReturnBookResponse_WhenBookExists() {
        // given
        BookRequest updatedRequest = BookTestData.builder().withTitle("new title").build().getRequest();
        Book originalBook = BookTestData.builder().build().getBook();
        Author author = AuthorTestData.builder().build().getAuthor();

        Book updatedBook = BookTestData.builder()
                .withId(1L)
                .withTitle("new title")
                .withAuthor(author)
                .build()
                .getBook();

        BookResponse updatedResponse = BookTestData.builder().withTitle("new title").build().getResponse();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(originalBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.toResponse(any(Book.class))).thenReturn(updatedResponse);

        when(authorService.getOrCreateAuthor(updatedRequest.author())).thenReturn(author);

        // when
        BookResponse result = bookService.updateBook(1L, updatedRequest);

        // then
        assertEquals(updatedResponse, result);
        verify(bookRepository).findById(1L);
        verify(bookMapper).toResponse(updatedBook);
        verify(bookRepository).save(updatedBook);
    }




    @Test
    void updateBook_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        // given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when, then
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, bookRequest));
        verify(bookRepository).findById(1L);
    }

    @Test
    void deleteBook_ShouldDeleteBook_WhenBookExists() {
        // given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        // when
        bookService.deleteBook(1L);

        // then
        verify(bookRepository).delete(book);
    }

    @Test
    void deleteBook_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        // given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when, the
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository).findById(1L);
    }


}