package com.dalto.library_api.service;

import com.dalto.library_api.model.Book;
import java.util.List;

public interface IBookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book addBook(Book book);
    void deleteBook(Long id);
    Book borrowBook(Long bookId, Long memberId);
    Book returnBook(Long bookId);
}
