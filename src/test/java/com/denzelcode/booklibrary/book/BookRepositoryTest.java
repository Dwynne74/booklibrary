package com.denzelcode.booklibrary.book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfBookExistsByIsbn() {
        // given
        String isbn = "9876";
        Book book = new Book(
                "New Book",
                "Book author",
                isbn,
                LocalDate.of(2024, 4, 30));

        underTest.save(book);
        // when
        Optional<Book> exists = underTest.findByIsbn(isbn);
        // then
        assertThat(exists.isPresent()).isTrue();
    }

    @Test
    void itShouldCheckIfBookDoesNotExistsByIsbn() {
        // given
        String isbn = "9876";
        // when
        Optional<Book> exists = underTest.findByIsbn(isbn);
        // then
        assertThat(exists.isPresent()).isFalse();
    }

    @Test
    void itShouldCheckIfBookExistsByTitle() {
        // given
        String title = "New Book";
        Book book = new Book(
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));
        underTest.save(book);
        // when
        Optional<Book> exists = underTest.findByTitle(title);
        // then
        assertThat(exists.isPresent()).isTrue();
    }

    @Test
    void itShouldCheckIfBookDoesNotExistsByTitle() {
        // given
        String title = "New Book";
        // when
        Optional<Book> exists = underTest.findByTitle(title);
        // then
        assertThat(exists.isPresent()).isFalse();
    }
}