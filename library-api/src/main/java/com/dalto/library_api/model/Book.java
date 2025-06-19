package com.dalto.library_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;


@Entity
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;

    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member borrowedBy;

    public Book() {}

    public Book(String title, String isbn, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.authors = new HashSet<>();
        if (author != null) {
            this.authors.add(author);
        }
        this.borrowedBy = null;
    }

    public Book(String title, String isbn, Set<Author> authors) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.borrowedBy = null;
    }


    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public Member getBorrowedBy() {
        return this.borrowedBy;
    }

    public Set<Author> getAuthors() {
        return this.authors;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void borrow(Member member) {
        if (this.borrowedBy != null) {
            throw new IllegalStateException("Book is already borrowed.");
        }
        this.borrowedBy = member;
    }

    public void returnBook() {
        if (this.borrowedBy == null) {
            throw new IllegalStateException("Book is not currently borrowed.");
        }
        this.borrowedBy = null;
    }

    public boolean isBorrowed() {
        return this.borrowedBy != null;
    }



}