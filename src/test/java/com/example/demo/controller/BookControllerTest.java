package com.example.demo.controller;


import com.example.demo.entities.Book;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId("1");
        book.setTitle("Mockito in Action");
        book.setAuthor("John Doe");
        book.setPrice(29.99);
    }

    @Test
    void testCreateBook() {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.createBook(book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mockito in Action", response.getBody().getTitle());
        verify(bookService, times(1)).createBook(book);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(book, new Book("2", "JUnit Testing", "Jane Doe", 35.50));
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById() {
        when(bookService.getBookById("1")).thenReturn(book);

        ResponseEntity<Book> response = bookController.getBookById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getId());
        verify(bookService, times(1)).getBookById("1");
    }

    @Test
    void testUpdateBook() {
        Book updatedBook = new Book("1", "Updated Title", "John Doe", 39.99);
        when(bookService.updateBook(eq("1"), any(Book.class))).thenReturn(updatedBook);

        ResponseEntity<Book> response = bookController.updateBook("1", updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Title", response.getBody().getTitle());
        verify(bookService, times(1)).updateBook("1", updatedBook);
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookService).deleteBook("1");

        ResponseEntity<Void> response = bookController.deleteBook("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).deleteBook("1");
    }
}