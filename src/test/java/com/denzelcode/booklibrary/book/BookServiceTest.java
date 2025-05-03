package com.denzelcode.booklibrary.book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    private BookService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BookService(bookRepository);
    }

    @Test
    void canGetAllBooks() {
        // when
        underTest.getAllBooks();
        // then
        verify(bookRepository).findAll();
    }

    @Test
    void canAddBook() {
        // given
        Book book = new Book(
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));
        // when
        underTest.addBook(book);

        // then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void willThrowExceptionIfBookAlreadyExists() {
        // given
        Book book = new Book(
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.findByIsbn(book.getIsbn()))
                .willReturn(Optional.of(book));
        // when
        // then
        assertThatThrownBy(() -> underTest.addBook(book))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ISBN already exists");

        verify(bookRepository, never()).save(any());
    }


    @Test
    void canDeleteBook() {
        // given
        Book book = new Book(
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.existsById(book.getId())).willReturn(true);

        // when
        underTest.deleteBook(book.getId());

        // then
        verify(bookRepository).deleteById(book.getId());
    }

    @Test
    void willThrowExceptionIfBookDoesNotExist() {
        // given
        Book book = new Book(
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.existsById(book.getId())).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> underTest.deleteBook(book.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book does not exist");

        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    void canUpdateBook() {
        // given
        Book book = new Book(
                1L,
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

        // when
        underTest.updateBook(1L, "Not A New Book", "6789");

        // then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void willThrowExceptionIfBookIdDoesNotExist() {
        // given
        given(bookRepository.findById(2L)).willReturn(Optional.empty());

        // when
        // then
       assertThatThrownBy(() -> underTest
               .updateBook(2L, "Not A New Book", "6789"))
               .isInstanceOf(IllegalArgumentException.class)
               .hasMessage("Book with " + 2L + " does not exist");

       verify(bookRepository, never()).save(any());
    }

    @Test
    void willNotUpdateBookEmptyTitleAndIsbnBook() {
        // given
        Book book = new Book(
                1L,
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

        // when
        underTest.updateBook(1L, "", "");

        // then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void willNotUpdateBookThatHasTheSameTitle() {
        // given
        Book book = new Book(
                1L,
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

        // when
        underTest.updateBook(1L, "New Book", "");

        // then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void willNotUpdateBookNullTitleAndIsbnBook() {
        // given
        Book book = new Book(
                1L,
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

        // when
        underTest.updateBook(1L, null, null);

        // then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void willThrowExceptionIfUpdateOfIsbnAlreadyExists() {
        // given
        Book book = new Book(
                1L,
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
        given(bookRepository.findByIsbn("1234")).willReturn(Optional.of(new Book()));

        // when
        // then
        assertThatThrownBy(() -> underTest.updateBook(1L, "", "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Isbn already exists");
    }

    @Test
    void willNotUpdateIfItHasTheSameIsbn() {
        // given
        Book book = new Book(
                1L,
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

        // when
        underTest.updateBook(1L, "", "9876");

        // then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }


    @Test
    void canGetBookByTitle() {
        // given
        Book book = new Book(
                1L,
                "New Book",
                "Book author",
                "9876",
                LocalDate.of(2024, 4, 30));

        given(bookRepository.findByTitle("New Book")).willReturn(Optional.of(book));

        // when
        Book result = underTest.getBookByTitle("New Book");

        // then
        verify(bookRepository).findByTitle("New Book");
        assertThat(result).isEqualTo(book);
    }

    @Test
    void willThrowExceptionIfBookTitleNotFound() {
        // given
        given(bookRepository.findByTitle("New Book")).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest.getBookByTitle("New Book"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book with " + "New Book" + " does not exist");
    }
}