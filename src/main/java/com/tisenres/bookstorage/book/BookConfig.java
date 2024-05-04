package com.tisenres.bookstorage.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BookConfig {

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
        return args -> {
            Book book1 = new Book(
                    "Crime and Punishment", "F. Dostoevsky", null
            );
            Book book2 = new Book(
                    "Anna Karenina", "L. Tolstoy", null
            );
            Book book3 = new Book(
                    "The Brothers Karamazov", "F. Dostoevsky", null
            );
            Book book4 = new Book(
                    "War and Peace", "L. Tolstoy", null
            );
            Book book5 = new Book(
                    "Dead Souls", "N. Gogol", null
            );

            bookRepository.saveAll(List.of(
                    book1, book2, book3, book4, book5
                )
            );
        };
    }
}
