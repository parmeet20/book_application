package com.example.demo.service;

import com.example.demo.entities.Book;

import java.util.List;

public interface BookService {

    Book createBook(Book book);

    Book getBookById(String id);

    List<Book> getAllBooks();

    Book updateBook(String id, Book book);

    void deleteBook(String id);
}
