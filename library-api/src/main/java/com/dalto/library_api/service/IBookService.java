package com.dalto.library_api.service;

import java.util.List;

import com.dalto.library_api.dto.BookDTO;
import com.dalto.library_api.model.Book;

public interface IBookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book addBook(Book book);
    void deleteBook(Long id);
    Book borrowBook(Long bookId, Long memberId);
    Book returnBook(Long bookId);
    List<BookDTO> getAllBooksAsDTOs();
    BookDTO getBookDTOById(Long id);
}
