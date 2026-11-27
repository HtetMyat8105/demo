package javaee8.com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import javaee8.com.example.demo.model.Book;
import javaee8.com.example.demo.repository.BookDao;
import javaee8.com.example.demo.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override public List<Book> getAllBooks() { return bookDao.getAllBooks(); }
    @Override public void saveBook(Book book) { bookDao.saveBook(book); }
    @Override public void deleteBookById(long id) { bookDao.deleteBookById(id); }
    @Override public Optional<Book> getBookById(long id) { return bookDao.getBookById(id); }
    @Override public void updateBook(Book book) { bookDao.updateBook(book); }
}