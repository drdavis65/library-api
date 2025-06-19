package com.dalto.library_api.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String borrowedBy;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String author, String isbn, String borrowedBy) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.borrowedBy = borrowedBy;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
