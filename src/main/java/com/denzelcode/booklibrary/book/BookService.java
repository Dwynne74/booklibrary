package com.denzelcode.booklibrary.book;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();

    }

    public void addBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());
        if (bookOptional.isPresent()) {
            throw new IllegalArgumentException("ISBN already exists");
        }
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        boolean bookExist = bookRepository.existsById(bookId);
        if (!bookExist) {
            throw new IllegalArgumentException("Book does not exist");
        }
        bookRepository.deleteById(bookId);
    }

    @Transactional
    public void updateBook(Long bookId, String title, String isbn) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with " + bookId + " does not exist"));

        if(title != null && !title.isEmpty() &&
            !Objects.equals(book.getTitle(), title)) {
            book.setTitle(title);
        }
        if(isbn != null && !isbn.isEmpty() &&
            !Objects.equals(book.getIsbn(), isbn)) {
            Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
            if (bookOptional.isPresent()) {
                throw new IllegalArgumentException("Isbn already exists");
            }
            book.setIsbn(isbn);
        }

        bookRepository.save(book);
    }

    public Book getBookByTitle(String title) {
        Optional<Book> bookOptional = bookRepository.findByTitle(title);

        if (bookOptional.isPresent()) {
            return bookOptional.get();
        } else {
            throw new IllegalArgumentException("Book with " + title + " does not exist");
        }
    }
}
