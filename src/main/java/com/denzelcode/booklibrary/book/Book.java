package com.denzelcode.booklibrary.book;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publishDate;

    @Transient
    private Integer duration;

    public Book(Long id,
                String title,
                String author,
                String isbn,
                LocalDate publishDate) {
        this.title = title;
        this.id = id;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    public Book() {
    }

    public Book(String title,
                String author,
                String isbn,
                LocalDate publishDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getDuration() {
        return Period.between(this.publishDate, LocalDate.now()).getYears();
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
