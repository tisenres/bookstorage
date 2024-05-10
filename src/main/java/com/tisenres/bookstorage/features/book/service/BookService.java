package com.tisenres.bookstorage.features.book.service;

import com.tisenres.bookstorage.features.book.dao.BookDAO;
import com.tisenres.bookstorage.features.book.model.Author;
import com.tisenres.bookstorage.features.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookDAO bookDAO;

    @Autowired
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getBooksByDescendingOrder() {
        return bookDAO.findAllInDescendingOfTitle();
    }

    public void addBook(Book book) {
        Optional<Book> optionalBook = bookDAO.findBooksByTitleAndAuthor(
                book.getTitle(), book.getAuthor()
        );
        if (optionalBook.isPresent()) {
            return;
        }
        bookDAO.saveBook(book);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookDAO.findBooksByAuthor(author);
    }

    public List<Author> getAuthorsBySymbol(String symbol, int limit) {
        Map<String, Integer> allAuthorsBySymbol = getAllAuthorsBySymbol(symbol);
        Map<String, Integer> limitedAuthorsBySymbol = getLimitedAuthorsBySymbol(allAuthorsBySymbol, limit);

        return limitedAuthorsBySymbol
                .entrySet()
                .stream()
                .map(entry -> new Author(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getAllAuthorsBySymbol(String symbol) {
        List<String> authors = bookDAO.findDistinctAuthors();

        return authors
                .stream()
                .collect(Collectors.toMap(
                                author -> author,
                                author -> bookDAO.findBooksByAuthor(author)
                                        .stream()
                                        .map(book -> book.getTitle().length() - book.getTitle().toLowerCase().replace(symbol.toLowerCase(), "").length())
                                        .reduce(0, Integer::sum)
                        )
                );
    }

    public Map<String, Integer> getLimitedAuthorsBySymbol(Map<String, Integer> authorsBySymbolsCounter, int limit) {
        return authorsBySymbolsCounter
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }
}
