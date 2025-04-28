package com.denzelcode.booklibrary.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/va1/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "{title}")
    public Book getBookByTitle(@PathVariable("title") String title) {
        return bookService.getBookByTitle(title);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @DeleteMapping(path = "{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
    }

    @PutMapping(path = "{bookId}")
    public void updateBook(@PathVariable("bookId") Long bookId,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) String isbn) {
        bookService.updateBook(bookId, title, isbn);
    }


}
