package com.tisenres.bookstorage.features.book.service;

import com.tisenres.bookstorage.features.book.repository.BookRepository;
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

//    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksByDescendingOrder() {
        return bookRepository.findAllInDescendingOfTitle();
    }

    public void addBook(Book book) {
        Optional<Book> optionalBook = bookRepository.findBookByTitleAndAuthor(
                book.getTitle(), book.getAuthor()
        );
        if (optionalBook.isPresent()) {
            return;
        }
        bookRepository.save(book);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
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
        List<String> authors = bookRepository.findDistinctAuthors();

        return authors.stream()
                .collect(Collectors.toMap(
                                author -> author,
                                author -> bookRepository.findBooksByAuthor(author)
                                        .stream()
                                        .map(book -> book.getTitle().length() - book.getTitle().toLowerCase().replace(symbol.toLowerCase(), "").length())
                                        .reduce(0, Integer::sum)
                        )
                );
    }

    public Map<String, Integer> getLimitedAuthorsBySymbol(Map<String, Integer> authorsBySymbolsCounter, int limit) {
        return authorsBySymbolsCounter.entrySet().stream()
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
