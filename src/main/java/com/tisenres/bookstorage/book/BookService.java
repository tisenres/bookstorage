package com.tisenres.bookstorage.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Map<String, Integer> getAuthorsBySymbol(String symbol) {

        List<String> authors = bookRepository.findDistinctAuthors();

        Map<String, Integer> collect = authors.stream()
                .collect(Collectors.toMap(author -> author, bookRepository::findBooksByAuthor))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().stream().map(
                                                book -> book.getTitle().length() - book.getTitle().replace(symbol, "").length())
                                        .reduce(0, Integer::sum)
                        )
                );

//        collect.forEach((key, value) -> System.out.println(key + " " + value));

        return collect;
    }
}
