package com.tisenres.bookstorage.features.book.dao;

import com.tisenres.bookstorage.features.book.model.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.tisenres.bookstorage.features.book.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAOImpl implements BookDAO {

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BOOKS_DESC = "SELECT * FROM book ORDER BY title DESC";
    private final String SQL_FIND_BOOKS_BY_TITLE_DESC = "SELECT * FROM book WHERE title = ? AND author = ?";
    private final String SQL_FIND_BOOKS_BY_AUTHOR = "SELECT * FROM book WHERE author = ?";
    private final String SQL_FIND_AUTHORS_DISTINCT = "SELECT DISTINCT author FROM book";
    private final String SQL_INSERT_BOOK = "INSERT INTO book(title, author, description) VALUES (?,?,?)";

    @Autowired
    public BookDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Book> findAllInDescendingOfTitle() {
        return jdbcTemplate.query(SQL_FIND_BOOKS_DESC, new BookMapper());
    }

    @Override
    public Optional<Book> findBooksByTitleAndAuthor(String title, String author) {
        List<Book> books = jdbcTemplate.query(SQL_FIND_BOOKS_BY_TITLE_DESC, new BookMapper(), title, author);
        if (books.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(books.get(0));
        }
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return jdbcTemplate.query(SQL_FIND_BOOKS_BY_AUTHOR, new BookMapper(), author);
    }

    @Override
    public List<String> findDistinctAuthors() {
        return jdbcTemplate.queryForList(SQL_FIND_AUTHORS_DISTINCT, String.class);
    }

    @Override
    public boolean saveBook(Book book) {
        return jdbcTemplate.update(SQL_INSERT_BOOK, book.getTitle(), book.getAuthor(), book.getDescription()) > 0;
    }
}
