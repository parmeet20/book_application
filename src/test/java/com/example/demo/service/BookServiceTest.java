package com.example.demo.service;

import com.example.demo.entities.Book;
import com.example.demo.repo.BookRepo;
import com.example.demo.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId("1");
        book.setTitle("Test Book");
        book.setAuthor("John Doe");
        book.setPrice(19.99);
    }

    @Test
    void testCreateBook() {
        when(bookRepo.save(book)).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        assertNotNull(createdBook);
        assertEquals("Test Book", createdBook.getTitle());
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void testGetBookById_Success() {
        when(bookRepo.findById("1")).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById("1");

        assertNotNull(foundBook);
        assertEquals("John Doe", foundBook.getAuthor());
        verify(bookRepo, times(1)).findById("1");
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepo.findById("2")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.getBookById("2"));

        assertEquals("Book not found", exception.getMessage());
        verify(bookRepo, times(1)).findById("2");
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(book, new Book("2", "Another Book", "Jane Doe", 25.50));
        when(bookRepo.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    void testUpdateBook_Success() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPrice(29.99);

        when(bookRepo.findById("1")).thenReturn(Optional.of(book));
        when(bookRepo.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook("1", updatedBook);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        verify(bookRepo, times(1)).findById("1");
        verify(bookRepo, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook_NotFound() {
        when(bookRepo.findById("99")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.updateBook("99", book));

        assertEquals("Book not found", exception.getMessage());
        verify(bookRepo, times(1)).findById("99");
        verify(bookRepo, never()).save(any(Book.class));
    }

    @Test
    void testDeleteBook_Success() {
        when(bookRepo.findById("1")).thenReturn(Optional.of(book));
        doNothing().when(bookRepo).delete(book);

        assertDoesNotThrow(() -> bookService.deleteBook("1"));
        verify(bookRepo, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepo.findById("99")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.deleteBook("99"));

        assertEquals("Book not found", exception.getMessage());
        verify(bookRepo, never()).delete(any(Book.class));
    }
}
