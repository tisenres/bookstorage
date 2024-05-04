package com.tisenres.bookstorage.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT book FROM Book book ORDER BY book.title DESC")
    List<Book> findAllInDescendingOfTitle();

    @Query("SELECT book FROM Book book WHERE book.title = ?1 AND book.author = ?2")
    Optional<Book> findStudentByTitleAndAuthor(String title, String author);

    @Query("SELECT book FROM Book book WHERE book.author = ?1")
    List<Book> findBooksByAuthor(String author);

    @Query("SELECT DISTINCT author FROM Book")
    List<String> findDistinctAuthors();
}
