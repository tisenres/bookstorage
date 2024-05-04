package com.tisenres.bookstorage.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT book FROM Book book ORDER BY book.title DESC")
    public List<Book> findAllInDescendingOrder();
}
