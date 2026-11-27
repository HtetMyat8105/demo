package javaee8.com.example.demo.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javaee8.com.example.demo.model.Book;
import javaee8.com.example.demo.repository.BookDao;

@Repository
public class BookJdbcDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookJdbcDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setYear(rs.getInt("year"));
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM books ORDER BY id", this::mapRowToBook);
    }

    @Override
    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO books (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    @Override
    public void deleteBookById(long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        List<Book> books = jdbcTemplate.query(
                "SELECT * FROM books WHERE id = ?", this::mapRowToBook, id);
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update("UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getYear(), book.getId());
    }
}