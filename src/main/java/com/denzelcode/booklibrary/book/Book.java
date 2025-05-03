package com.denzelcode.booklibrary.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;

@Entity(name = "Book")
@Table(
        name = "book",
        uniqueConstraints = {
                @UniqueConstraint(name = "book_isbn_unique", columnNames = "isbn")
        }
)
@Getter
@Setter
@ToString
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
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column(
            name = "author",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String author;

    @Column(
            name = "isbn",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String isbn;

    @Column(
            name = "publish_date",
            nullable = false
    )
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

    public Integer getDuration() {
        return Period.between(this.publishDate, LocalDate.now()).getYears();
    }
}
