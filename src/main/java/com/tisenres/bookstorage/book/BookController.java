package com.tisenres.bookstorage.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping(path ="/booksByAuthor")
    public List<Book> getBooksByAuthor(@RequestParam("author") String author) {
        return bookService.getBooksByAuthor(author);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }
}
