package com.tisenres.bookstorage.features.book.dao;

import com.tisenres.bookstorage.features.book.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
    List<Book> findAllInDescendingOfTitle();

    Optional<Book> findBooksByTitleAndAuthor(String title, String author);

    List<Book> findBooksByAuthor(String author);

    List<String> findDistinctAuthors();

    boolean saveBook(Book book);
}
