package com.example.demo.repo;

import com.example.demo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,String> {
    List<Book> findByAuthor(String author);
}
