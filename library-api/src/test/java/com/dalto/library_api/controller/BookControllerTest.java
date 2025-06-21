package com.dalto.library_api.controller;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dalto.library_api.dto.BookDTO;
import com.dalto.library_api.model.Book;
import com.dalto.library_api.service.IBookService;

public class BookControllerTest {

    @Test
    public void testGetAllBooks() {
        // Arrange
        IBookService mockService = mock(IBookService.class);
        BookController controller = new BookController(mockService);

        BookDTO book1 = new BookDTO(1L, "Test Book", "Author", "123456", null);
        BookDTO book2 = new BookDTO(2L, "Another Book", "Author", "789012", null);

        when(mockService.getAllBooksAsDTOs()).thenReturn(List.of(book1, book2));

        // Act
        List<BookDTO> result = controller.getAllBooks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(mockService, times(1)).getAllBooksAsDTOs();
    }

    @Test
    public void testGetBookById() {
        IBookService mockService = mock(IBookService.class);
        BookController controller = new BookController(mockService);

        BookDTO mockDTO = new BookDTO(1L, "Test Book", "Author", "123456", null);
        when(mockService.getBookDTOById(1L)).thenReturn(mockDTO);

        ResponseEntity<BookDTO> response = controller.getBookById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("Test Book", response.getBody().getTitle());

    }

    @Test
    public void testAddBook() {
        IBookService mockService = mock(IBookService.class);
        BookController controller = new BookController(mockService);

        Book newBook = new Book("New Book", "1234567890", new HashSet<>());

        newBook.setId(1L);

        when(mockService.addBook(newBook)).thenReturn(newBook);

        ResponseEntity<Book> response = controller.addBook(newBook);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Book", response.getBody().getTitle());
        verify(mockService, times(1)).addBook(newBook);

    }

    @Test
    public void testDeleteBook() {
        IBookService mockService = mock(IBookService.class);
        BookController controller = new BookController(mockService);

        Long bookId = 1L;

        ResponseEntity<Void> response = controller.deleteBook(bookId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(mockService, times(1)).deleteBook(bookId);
    }

    @Test
    public void testBorrowBook() {
        IBookService mockService = mock(IBookService.class);
        BookController controller = new BookController(mockService);

        Book mockBook = new Book("Borrowed Book", "1234567890", new HashSet<>());
        mockBook.setId(1L);

        when(mockService.borrowBook(1L, 1L)).thenReturn(mockBook);

        ResponseEntity<Book> response = controller.borrowBook(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Borrowed Book", response.getBody().getTitle());
        verify(mockService, times(1)).borrowBook(1L, 1L);
    }

    @Test
    public void testReturnBook() {
        IBookService mockService = mock(IBookService.class);
        BookController controller = new BookController(mockService);

        Book mockBook = new Book("Returned Book", "1234567890", new HashSet<>());
        mockBook.setId(1L);

        when(mockService.returnBook(1L)).thenReturn(mockBook);

        ResponseEntity<Book> response = controller.returnBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Returned Book", response.getBody().getTitle());
        verify(mockService, times(1)).returnBook(1L);
    }
}
