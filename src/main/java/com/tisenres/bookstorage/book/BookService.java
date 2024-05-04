package com.tisenres.bookstorage.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAllInDescendingOrderOfTitle();
    }

    public void addBook(Book book) {
        Optional<Book> optionalBook = bookRepository.findStudentByTitleAndAuthor(
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

    public Map<String, Integer> getAuthorsBySymbol(String symbol, int limit) {
        List<String> authors = bookRepository.findDistinctAuthors();

        Map<String, Integer> authorsBySymbolsCounter = authors.stream()
                .collect(Collectors.toMap(
                                author -> author,
                                author -> bookRepository.findBooksByAuthor(author)
                                        .stream()
                                        .map(book -> book.getTitle().length() - book.getTitle().replace(symbol, "").length())
                                        .reduce(0, Integer::sum)
                        )
                );
        return getAuthorsBySymbolTop(authorsBySymbolsCounter, limit);
    }

    public Map<String, Integer> getAuthorsBySymbolTop(Map<String, Integer> authorsBySymbolsCounter, int limit) {
        Map<String, Integer> authorsBySymbolsTop = authorsBySymbolsCounter.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
        return authorsBySymbolsTop;
    }
}
