package com.denzelcode.booklibrary.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class BookConfig {

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
        return args -> {
            Book manga = new Book(
                    "One Piece",
                    "Oda",
                    "1234",
                    LocalDate.of(1998, 10, 8));

            Book comic = new Book(
                    "Marvel",
                    "marv",
                    "2345",
                    LocalDate.of(2002, 6, 22));

            bookRepository.saveAll(
                    List.of(manga, comic)
            );
        };
    }
}
