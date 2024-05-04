package com.tisenres.bookstorage.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/books")
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

    @GetMapping(path = "/books-by-author")
    public List<Book> getBooksByAuthor(@RequestParam("author") String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping(path = "/authors-by-symbol")
    public Map<String, Integer> getAuthorsBySymbol(@RequestParam("symbol") String symbol,
                                                   @RequestParam("limit") int limit) {
        return bookService.getAuthorsBySymbol(symbol, limit);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }
}
