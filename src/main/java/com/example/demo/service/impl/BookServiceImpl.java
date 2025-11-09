package com.example.demo.service.impl;

import com.example.demo.entities.Book;
import com.example.demo.repo.BookRepo;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Override
    public Book createBook(Book book) {
        // Using method reference with map to simulate some processing (if needed)
        return bookRepo.save(book);
    }

    @Override
    public Book getBookById(String id) {
        // Using method reference for exception
        return bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public List<Book> getAllBooks() {
        // Using method reference to collect results
        return bookRepo.findAll();
    }

    @Override
    public Book updateBook(String id, Book book) {
        return bookRepo.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setPrice(book.getPrice());
                    return bookRepo.save(existingBook);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public void deleteBook(String id) {
        // Method reference directly
        bookRepo.findById(id).ifPresentOrElse(
                bookRepo::delete,
                () -> { throw new RuntimeException("Book not found"); }
        );
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepo.findByAuthor(author);
    }

}
