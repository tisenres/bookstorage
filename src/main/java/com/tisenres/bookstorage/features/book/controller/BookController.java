package com.tisenres.bookstorage.features.book.controller;

import com.tisenres.bookstorage.features.book.service.BookService;
import com.tisenres.bookstorage.features.book.model.Author;
import com.tisenres.bookstorage.features.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/books-desc")
    public List<Book> getBooksByDescendingOrder() {
        return bookService.getBooksByDescendingOrder();
    }

    @GetMapping(path = "/books-by-author")
    public List<Book> getBooksByAuthor(@RequestParam("author") String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping(path = "/authors-by-symbol-and-limit")
    public List<Author> getAuthorsBySymbol(@RequestParam("symbol") String symbol,
                                           @RequestParam("limit") int limit) {
        return bookService.getAuthorsBySymbol(symbol, limit);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }
}
