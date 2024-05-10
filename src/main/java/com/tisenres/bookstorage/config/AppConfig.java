package com.tisenres.bookstorage.config;

import com.tisenres.bookstorage.features.book.dao.BookDAO;
import com.tisenres.bookstorage.features.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.util.List;

@Configuration
@ComponentScan("com.tisenres.bookstorage")
@PropertySource("classpath:database.properties")
public class AppConfig {

    @Autowired
    Environment environment;

    private final String URL = "url";
    private final String USER = "dbuser";
    private final String DRIVER = "driver";
    private final String PASSWORD = "dbpassword";

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty(URL));
        driverManagerDataSource.setUsername(environment.getProperty(USER));
        driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
        driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
        return driverManagerDataSource;
    }

    @Bean
    CommandLineRunner commandLineRunner(BookDAO bookDAO) {
        return args -> {
            Book book1 = new Book(
                    0L, "Crime and Punishment", "F. Dostoevsky", null
            );
            Book book2 = new Book(
                    1L, "Anna Karenina", "L. Tolstoy", null
            );
            Book book3 = new Book(
                    2L, "The Brothers Karamazov", "F. Dostoevsky", null
            );
            Book book4 = new Book(
                    3L, "War and Peace", "L. Tolstoy", null
            );
            Book book5 = new Book(
                    4L, "Dead Souls", "N. Gogol", null
            );
            List<Book> books = List.of(
                    book1, book2, book3, book4, book5
            );
            books.forEach(bookDAO::saveBook);
        };
    }
}
