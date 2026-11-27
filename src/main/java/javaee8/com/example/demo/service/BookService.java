package javaee8.com.example.demo.service;

import java.util.List;
import java.util.Optional;
import javaee8.com.example.demo.model.Book;

public interface BookService {
    List<Book> getAllBooks();
    void saveBook(Book book);
    void deleteBookById(long id);
    Optional<Book> getBookById(long id);
    void updateBook(Book book);
}